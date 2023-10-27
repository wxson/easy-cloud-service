package com.easy.cloud.web.service.member.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.member.biz.domain.db.MemberDO;
import com.easy.cloud.web.service.member.biz.domain.db.MemberPropertyRecordDO;
import com.easy.cloud.web.service.member.biz.domain.db.VipRechargeDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberPropertyRecordDTO;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberPropertyRecordVO;
import com.easy.cloud.web.service.member.biz.mapper.DbMemberMapper;
import com.easy.cloud.web.service.member.biz.mapper.DbMemberPropertyRecordMapper;
import com.easy.cloud.web.service.member.biz.mapper.DbMemberStorageMapper;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import com.easy.cloud.web.service.member.biz.service.IVipRechargeService;
import com.easy.cloud.web.service.upms.api.domain.User;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl extends ServiceImpl<DbMemberMapper, MemberDO> implements IMemberService {

    private final DbMemberPropertyRecordMapper memberPropertyRecordMapper;

    private final DbMemberStorageMapper memberStorageMapper;

    private final UpmsFeignClientService upmsFeignClientService;

    private final IVipRechargeService vipRechargeService;

    @Override
    public MemberDO verifyBeforeSave(MemberDO memberDO) {
        MemberDO existMember = this.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, memberDO.getUserId()));
        if (null != existMember) {
            throw new BusinessException("当前会员信息已存在");
        }
        return memberDO;
    }

    @Override
    public void updateMemberVipLevel(MemberDO memberDO) {
        // 获取当前玩家总的消费水平
        BigDecimal totalRecharge = Optional.ofNullable(memberDO.getTotalRecharge()).orElse(BigDecimal.ZERO);
        // 获取当前消费水平对应的VIP等级
        Optional<VipRechargeDO> optionalVipRechargeDO = vipRechargeService.list(Wrappers.<VipRechargeDO>lambdaQuery()
                .ge(VipRechargeDO::getTotalRecharge, totalRecharge))
                .stream()
                .min(Comparator.comparing(VipRechargeDO::getTotalRecharge));
        if (optionalVipRechargeDO.isPresent()) {
            VipRechargeDO vipRechargeDO = optionalVipRechargeDO.get();
            if (!memberDO.getVipLevel().equals(vipRechargeDO.getVipLevel())) {
                // 更新会员VIP等级
                memberDO.setVipLevel(vipRechargeDO.getVipLevel());
                this.updateById(memberDO);
            }
        }
    }

    @Async
    @Override
    public void insertMemberPropertyRecord(MemberDTO memberDTO) {
        MemberPropertyRecordDO memberPropertyRecordDO = MemberPropertyRecordDO.build()
                .setUserId(SecurityUtils.getAuthenticationUser().getId())
                .setOrigin(memberDTO.getOrigin())
                .setOrderNo(memberDTO.getOrderNo())
                .setRecharge(BigDecimal.ZERO)
                .setAmount(GlobalCommonConstants.L_ZERO)
                .setDiamond(GlobalCommonConstants.L_ZERO)
                .setCoupon(GlobalCommonConstants.L_ZERO);
        // 资产累加
        Optional.ofNullable(memberDTO.getRecharge()).ifPresent(memberPropertyRecordDO::setRecharge);
        Optional.ofNullable(memberDTO.getAmount()).ifPresent(memberPropertyRecordDO::setAmount);
        Optional.ofNullable(memberDTO.getDiamond()).ifPresent(memberPropertyRecordDO::setDiamond);
        Optional.ofNullable(memberDTO.getCoupon()).ifPresent(memberPropertyRecordDO::setCoupon);
        // 插入记录
        memberPropertyRecordMapper.insert(memberPropertyRecordDO);
    }

    @Override
    public List<MemberPropertyRecordVO> memberPropertyRecordList(MemberPropertyRecordDTO memberPropertyRecordDTO) {
        return memberPropertyRecordMapper.selectList(Wrappers.<MemberPropertyRecordDO>lambdaQuery()
                .eq(Objects.nonNull(memberPropertyRecordDTO.getOrigin()), MemberPropertyRecordDO::getOrigin, memberPropertyRecordDTO.getOrigin())
                .eq(StrUtil.isNotBlank(memberPropertyRecordDTO.getUserId()), MemberPropertyRecordDO::getUserId, memberPropertyRecordDTO.getUserId())
                .ge(StrUtil.isNotBlank(memberPropertyRecordDTO.getMinCreateAt()), MemberPropertyRecordDO::getCreateAt, memberPropertyRecordDTO.getMinCreateAt())
                .lt(StrUtil.isNotBlank(memberPropertyRecordDTO.getMaxCreateAt()), MemberPropertyRecordDO::getCreateAt, memberPropertyRecordDTO.getMaxCreateAt()))
                .stream()
                .map(MemberPropertyRecordDO::convert).collect(Collectors.toList());
    }

    @Override
    public void verifyAfterDetail(IPage<MemberDO> page) {
        List<MemberDO> records = page.getRecords();
        List<String> userIdList = records.stream().map(MemberDO::getUserId).collect(Collectors.toList());
        List<User> userList = upmsFeignClientService.batchList(userIdList).getData();
        if (CollUtil.isNotEmpty(userList)) {
            Map<String, User> userIdMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user, (u1, u2) -> u1));
            for (MemberDO memberDO : records) {
                Optional.ofNullable(userIdMap.get(memberDO.getUserId())).ifPresent(user -> memberDO.setNickName(user.getNickName()).setAccount(user.getAccount()));
            }
        }
    }
}
