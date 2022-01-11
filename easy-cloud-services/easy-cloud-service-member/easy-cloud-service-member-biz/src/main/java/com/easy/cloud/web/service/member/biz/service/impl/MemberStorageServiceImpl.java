package com.easy.cloud.web.service.member.biz.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.member.biz.domain.db.MemberDO;
import com.easy.cloud.web.service.member.biz.domain.db.MemberStorageDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberStorageDTO;
import com.easy.cloud.web.service.member.biz.mapper.DbMemberStorageMapper;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import com.easy.cloud.web.service.member.biz.service.IMemberStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * 会员仓库业务逻辑
 *
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberStorageServiceImpl extends ServiceImpl<DbMemberStorageMapper, MemberStorageDO> implements IMemberStorageService {

    private final IMemberService memberService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveAmount(MemberStorageDTO memberStorageDTO) {
        // 获取用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 查询仓库状态
        MemberStorageDO memberStorageDO = this.getOne(Wrappers.<MemberStorageDO>lambdaQuery().eq(MemberStorageDO::getUserId, userId));
        if (StatusEnum.FORBID_STATUS.getCode() == memberStorageDO.getStatus()) {
            throw new BusinessException("当前仓库未解锁，暂时无法使用");
        }

        // 获取当前会员信息
        MemberDO memberDO = memberService.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, userId));
        // 用户存储的金额
        Long storeAmount = memberStorageDTO.getAmount();
        // 当前用的可用余额
        Long currentAmount = memberDO.getAmount();
        long offsetAmount = NumberUtil.sub(currentAmount, storeAmount).longValue();
        if (offsetAmount < 0) {
            throw new BusinessException("当前可用余额不足");
        }

        // 跟新现有的金额
        memberDO.setAmount(offsetAmount);
        memberService.updateById(memberDO);
        // 存储金额
        Long oldAmount = Optional.ofNullable(memberStorageDO.getAmount()).orElse(GlobalConstants.L_ZERO);
        memberStorageDO.setAmount(NumberUtil.add(oldAmount, storeAmount).longValue());
        this.updateById(memberStorageDO);
        return true;
    }

    @Override
    public Boolean takeOutAmount(MemberStorageDTO memberStorageDTO) {
        // 获取用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 查询仓库状态
        MemberStorageDO memberStorageDO = this.getOne(Wrappers.<MemberStorageDO>lambdaQuery().eq(MemberStorageDO::getUserId, userId));
        if (StatusEnum.FORBID_STATUS.getCode() == memberStorageDO.getStatus()) {
            throw new BusinessException("当前仓库未解锁，暂时无法使用");
        }

        // 获取取出的金额
        Long outAmount = Optional.ofNullable(memberStorageDTO.getAmount()).orElse(GlobalConstants.L_ZERO);
        // 当前仓库中的剩余金额
        Long surplusAmount = memberStorageDO.getAmount();

        long offsetAmount = NumberUtil.sub(surplusAmount, outAmount).longValue();
        if (offsetAmount < GlobalConstants.L_ZERO) {
            throw new BusinessException("当前取出金额大于剩余金额");
        }

        // 获取会员信息
        MemberDO memberDO = memberService.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, userId));
        if (Objects.isNull(memberDO)) {
            throw new BusinessException("获取会员信息错误");
        }

        // 更新仓库数据
        this.update(Wrappers.<MemberStorageDO>lambdaUpdate().eq(MemberStorageDO::getUserId, userId)
                .set(MemberStorageDO::getAmount, offsetAmount));
        // 更新会员数据
        Optional.ofNullable(memberDO.getAmount()).ifPresent(amount -> memberDO.setAmount(NumberUtil.add(memberDO.getAmount(), outAmount).longValue()));
        memberService.updateById(memberDO);

        return true;
    }

    @Override
    public MemberStorageDO initMemberStorage(String userId) {
        if (StrUtil.isBlank(userId)) {
            throw new BusinessException("当前用户ID不能为空");
        }

        MemberStorageDO storageDO = this.getOne(Wrappers.<MemberStorageDO>lambdaQuery().eq(MemberStorageDO::getUserId, userId));
        if (Objects.nonNull(storageDO)) {
            return storageDO;
        }

        // 新增会员后初始化仓库信息，此时仓库为未解锁状态
        MemberStorageDO memberStorageDO = MemberStorageDO.build()
                .setUserId(userId)
                .setStatus(StatusEnum.FORBID_STATUS.getCode())
                .setAmount(GlobalConstants.L_ZERO);
        this.save(memberStorageDO);
        return memberStorageDO;
    }
}
