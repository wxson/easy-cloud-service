package com.easy.cloud.web.service.member.biz.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.member.biz.domain.db.MemberDO;
import com.easy.cloud.web.service.member.biz.domain.db.VipRechargeDO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.biz.domain.dto.MemberPropertyRecordDTO;
import com.easy.cloud.web.service.member.biz.domain.query.MemberQuery;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberPropertyRecordVO;
import com.easy.cloud.web.service.member.biz.domain.vo.MemberVO;
import com.easy.cloud.web.service.member.biz.service.IMemberService;
import com.easy.cloud.web.service.member.biz.service.IMemberStorageService;
import com.easy.cloud.web.service.member.biz.service.IVipRechargeService;
import com.easy.cloud.web.service.upms.api.domain.User;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author GR
 * @date 2021-11-28 18:02
 */
@Slf4j
@RestController
@RequestMapping("member")
@AllArgsConstructor
@Api(value = "????????????", tags = "????????????")
public class MemberController extends BaseController<MemberQuery, MemberDTO, MemberDO> {

    private final IMemberService memberService;

    private final UpmsFeignClientService upmsFeignClientService;

    private final IVipRechargeService vipRechargeService;

    private final IMemberStorageService memberStorageService;

    @Override
    public IRepositoryService<MemberDO> getService() {
        return memberService;
    }

    /**
     * ????????????ID??????????????????
     *
     * @param userId ??????ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @Inner
    @GetMapping("detail/{userId}")
    @ApiOperation(value = "????????????ID??????????????????")
    public HttpResult<MemberVO> getMemberDetailByUserId(@PathVariable @ApiParam("??????ID") String userId) {
        MemberDO memberDO = memberService.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, userId));
        if (null == memberDO) {
            throw new BusinessException("???????????????????????????");
        }

        // ??????????????????
        MemberVO memberVO = memberDO.convert();
        // ??????????????????
        User user = upmsFeignClientService.loadUserByUserId(userId).getData();
        if (null != user) {
            memberVO.setAccount(user.getAccount())
                    .setNickName(user.getNickName())
                    .setAvatar(user.getAvatar());
        }

        return HttpResult.ok(memberVO);
    }

    /**
     * ????????????ID??????????????????
     *
     * @param memberDTO ??????ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @Inner
    @PostMapping("detail")
    @ApiOperation(value = "????????????ID??????????????????")
    public HttpResult<MemberVO> getMemberDetail(@RequestBody MemberDTO memberDTO) {
        if (StrUtil.isBlank(memberDTO.getUserId())) {
            throw new BusinessException("????????????ID????????????");
        }

        return this.getMemberDetailByUserId(memberDTO.getUserId());
    }

    /**
     * ??????????????????
     *
     * @param memberDTO ????????????
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @GetMapping("update")
    @ApiOperation(value = "??????????????????")
    public HttpResult<MemberVO> updateMemberInfo(@RequestBody MemberDTO memberDTO) {
        MemberDO memberDO = memberDTO.convert();
        memberService.updateById(memberDO);
        return HttpResult.ok(memberDO.convert());
    }

    /**
     * ??????????????????
     *
     * @param memberDTO ????????????
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @PostMapping("property/update")
    @ApiOperation(value = "????????????????????????")
    public HttpResult<MemberVO> updateMemberProperty(@RequestBody MemberDTO memberDTO) {
        // ????????????????????????
        MemberDO memberDO = memberService.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, SecurityUtils.getAuthenticationUser().getId()));
        // ????????????
        Optional.ofNullable(memberDTO.getRecharge()).ifPresent(recharge -> memberDO.setTotalRecharge(NumberUtil.add(recharge, memberDO.getTotalRecharge())));
        Optional.ofNullable(memberDTO.getAmount()).ifPresent(amount -> memberDO.setAmount(NumberUtil.add(amount, memberDO.getAmount()).longValue()));
        Optional.ofNullable(memberDTO.getDiamond()).ifPresent(diamond -> memberDO.setDiamond(NumberUtil.add(diamond, memberDO.getDiamond()).longValue()));
        Optional.ofNullable(memberDTO.getCoupon()).ifPresent(coupon -> memberDO.setCoupon(NumberUtil.add(coupon, memberDO.getCoupon()).longValue()));
        memberService.updateById(memberDO);

        // ????????????????????????
        memberService.insertMemberPropertyRecord(memberDTO);
        return HttpResult.ok(memberDO.convert());
    }

    /**
     * ??????????????????????????????
     *
     * @param memberPropertyRecordDTO ????????????
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @PostMapping("property/record/list")
    @ApiOperation(value = "??????????????????????????????")
    public HttpResult<List<MemberPropertyRecordVO>> memberPropertyRecordList(@RequestBody MemberPropertyRecordDTO memberPropertyRecordDTO) {
        // ????????????????????????
        List<MemberPropertyRecordVO> list = memberService.memberPropertyRecordList(memberPropertyRecordDTO);
        return HttpResult.ok(list);
    }

    /**
     * ?????????????????????????????????????????????????????????VIP
     *
     * @param userId ????????????ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @GetMapping("calculate/next/vip/need/diamond/{userId}")
    @ApiOperation(value = "?????????????????????????????????????????????????????????VIP")
    public HttpResult<MemberVO> calculateNextVipNeedDiamond(@PathVariable String userId) {
        if (StrUtil.isBlank(userId)) {
            throw new BusinessException("??????ID????????????");
        }

        // ??????????????????
        MemberDO memberDO = memberService.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, userId));
        if (Objects.isNull(memberDO)) {
            throw new BusinessException("????????????????????????");
        }

        Integer vipLevel = Optional.ofNullable(memberDO.getVipLevel()).orElse(GlobalConstants.ZERO);
        Integer nextVipLevel = vipLevel + 1;
        List<VipRechargeDO> vipRechargeDOList = vipRechargeService.list(Wrappers.<VipRechargeDO>lambdaQuery().in(VipRechargeDO::getVipLevel, CollUtil.newArrayList(vipLevel, nextVipLevel)));
        Map<Integer, BigDecimal> vipLevelRechargeMap = vipRechargeDOList.stream().collect(Collectors.toMap(VipRechargeDO::getVipLevel, VipRechargeDO::getTotalRecharge, (v1, v2) -> v1));
        // ???????????????????????? 1:10
        long currentDiamondNum = Optional.ofNullable(vipLevelRechargeMap.get(vipLevel)).orElse(BigDecimal.ZERO).longValue() * 10;
        long nextVipDiamondNum = Optional.ofNullable(vipLevelRechargeMap.get(nextVipLevel)).orElse(BigDecimal.ZERO).longValue() * 10;
        return HttpResult.ok(MemberVO.build().setCurrentRechargeTotalDiamondNum(currentDiamondNum).setNextVipLevelDiamondNum(nextVipDiamondNum));
    }

    /**
     * ??????????????????
     *
     * @param memberDTO ????????????
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.biz.domain.vo.MemberVO>
     */
    @Inner
    @PostMapping("init")
    @ApiOperation(value = "?????????????????????")
    public HttpResult<MemberVO> initMemberInfo(@RequestBody MemberDTO memberDTO) {
        // ??????????????????
        MemberDO memberDO = memberService.getOne(Wrappers.<MemberDO>lambdaQuery().eq(MemberDO::getUserId, memberDTO.getUserId()));
        if (null != memberDO) {
            return HttpResult.ok(memberDO.convert());
        }

        memberDO = memberDTO.convert();
        memberService.save(memberDO);
        // ?????????????????????,??????????????????????????????????????????????????????
        memberStorageService.initMemberStorage(memberDO.getUserId());
        return HttpResult.ok(memberDO.convert());
    }
}
