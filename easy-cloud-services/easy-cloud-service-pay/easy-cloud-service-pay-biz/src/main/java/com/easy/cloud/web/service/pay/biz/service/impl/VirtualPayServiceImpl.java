package com.easy.cloud.web.service.pay.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.api.domain.vo.GoodsVO;
import com.easy.cloud.web.service.cms.api.enums.CurrencyTypeEnum;
import com.easy.cloud.web.service.cms.api.feign.CmsFeignClientService;
import com.easy.cloud.web.service.member.api.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.domain.vo.MemberVO;
import com.easy.cloud.web.service.member.api.enums.PropertyOriginEnum;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.order.api.enums.PayStatusEnum;
import com.easy.cloud.web.service.pay.biz.domain.dto.BillDTO;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.BillVO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;
import com.easy.cloud.web.service.pay.biz.service.IPayProxyService;
import com.easy.cloud.web.service.pay.biz.service.IPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 钻石支付
 *
 * @author GR
 * @date 2021-11-12 14:41
 */
@Slf4j
@Service
@AllArgsConstructor
public class VirtualPayServiceImpl implements IPayProxyService {

    private final MemberFeignClientService memberFeignClientService;

    private final IPayService payService;

    private final CmsFeignClientService cmsFeignClientService;

    @Override
    public PayVO pay(PayDTO payDTO) {
        // 获取当前会员信息
        MemberVO memberVO = memberFeignClientService.getMemberDetailByUserId(SecurityUtils.getAuthenticationUser().getId()).getData();
        if (null == memberVO) {
            throw new BusinessException("获取会员信息失败");
        }

        // 发放商品
        GoodsVO goodsVO = cmsFeignClientService.getGoodsDetailByNo(payDTO.getGoodsNo()).getData();
        if (null == goodsVO) {
            throw new BusinessException("当前商品信息不存在");
        }

        // 是否允许支付
        this.allowPay(goodsVO, memberVO);

        // 构建扣减的货币数据
        MemberDTO memberDTO = MemberDTO.build()
                // 此时钻石为支付货币
                .setOrigin(PropertyOriginEnum.PAY.getCode());
        // 根据类型设置扣减货币数据
        this.deductionCurrency(goodsVO, memberDTO);
        // 扣减钻石
        memberFeignClientService.updateMemberProperty(memberDTO);

        // 发放奖励
        payService.grantPrize(null, payDTO.getGoodsNo());
        return PayVO.build().setPayStatus(PayStatusEnum.PAY_YES);
    }

    /**
     * 是否允许支付
     *
     * @param goodsVO  商品
     * @param memberVO 会员
     */
    private void allowPay(GoodsVO goodsVO, MemberVO memberVO) {
        // 金币
        if (CurrencyTypeEnum.GOLD_COIN.getCode() == goodsVO.getCurrencyType()) {
            if (memberVO.getAmount() < goodsVO.getSalesPrice().longValue()) {
                throw new BusinessException("当前剩余金币不足");
            }
        }

        // 钻石
        if (CurrencyTypeEnum.DIAMOND.getCode() == goodsVO.getCurrencyType()) {
            if (memberVO.getDiamond() < goodsVO.getSalesPrice().longValue()) {
                throw new BusinessException("当前剩余钻石不足");
            }
        }

        // 点券
        if (CurrencyTypeEnum.COUPON.getCode() == goodsVO.getCurrencyType()) {
            if (memberVO.getCoupon() <= goodsVO.getSalesPrice().longValue()) {
                throw new BusinessException("当前剩余点券不足");
            }
        }
    }

    /**
     * 扣减货币
     *
     * @param goodsVO   商品信息
     * @param memberDTO 会员信息
     */
    private void deductionCurrency(GoodsVO goodsVO, MemberDTO memberDTO) {
        // 金币
        if (CurrencyTypeEnum.GOLD_COIN.getCode() == goodsVO.getCurrencyType()) {
            memberDTO.setAmount(-1 * goodsVO.getSalesPrice().longValue());
        }

        // 钻石
        if (CurrencyTypeEnum.DIAMOND.getCode() == goodsVO.getCurrencyType()) {
            memberDTO.setDiamond(-1 * goodsVO.getSalesPrice().longValue());
        }

        // 点券
        if (CurrencyTypeEnum.COUPON.getCode() == goodsVO.getCurrencyType()) {
            memberDTO.setCoupon(-1 * goodsVO.getSalesPrice().longValue());
        }
    }


    @Override
    public BillVO bill(BillDTO billDTO) {
        return null;
    }
}
