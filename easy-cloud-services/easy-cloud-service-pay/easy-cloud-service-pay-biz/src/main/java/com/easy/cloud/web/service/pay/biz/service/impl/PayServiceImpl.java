package com.easy.cloud.web.service.pay.biz.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.api.domain.dto.ActionDTO;
import com.easy.cloud.web.service.cms.api.domain.vo.GoodsVO;
import com.easy.cloud.web.service.cms.api.enums.ActionEnum;
import com.easy.cloud.web.service.cms.api.enums.CurrencyTypeEnum;
import com.easy.cloud.web.service.cms.api.enums.GoodsTypeEnum;
import com.easy.cloud.web.service.cms.api.feign.CmsFeignClientService;
import com.easy.cloud.web.service.member.api.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.enums.PropertyOriginEnum;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.order.api.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.enums.PayStatusEnum;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import com.easy.cloud.web.service.pay.biz.constants.PayConstants;
import com.easy.cloud.web.service.pay.biz.domain.dto.BillDTO;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.BillVO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;
import com.easy.cloud.web.service.pay.biz.enums.PayTypeEnum;
import com.easy.cloud.web.service.pay.biz.service.IPayProxyService;
import com.easy.cloud.web.service.pay.biz.service.IPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 支付
 *
 * @author GR
 * @date 2021-11-12 14:41
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayServiceImpl implements IPayService {

    private final OrderFeignClientService orderFeignClientService;

    private final MemberFeignClientService memberFeignClientService;

    private final CmsFeignClientService cmsFeignClientService;

    private final RedisTemplate redisTemplate;

    @Override
    public Object payCallbackHandler(PayDTO payDTO) {
        // 如果支付成功回调
        String orderNo = payDTO.getOrderNo();
        // 查看当前订单是否已回调过
        Object o = redisTemplate.opsForValue().get(StrUtil.format(PayConstants.ORDER_CALLBACK_NOTICE_KEY, orderNo));
        if (Objects.nonNull(o)) {
            log.info("当前支付订单：{} 回调通知已处理，禁止重复消费", orderNo);
            return true;
        }

        // 通知订单支付成功
        orderFeignClientService.paySuccessHandler(orderNo);
        // 获取当前订单的详情
        OrderVO orderVO = orderFeignClientService.getOrderDetailByNo(orderNo).getData();
        if (Objects.isNull(orderVO)) {
            throw new BusinessException("当前订单信息不存在");
        }

        // 支付成功，发放奖励
        this.grantPrize(orderVO.getNo(), orderVO.getGoodsNo());
        // 标记订单已处理，有效时间1天
        redisTemplate.opsForValue().set(StrUtil.format(PayConstants.ORDER_CALLBACK_NOTICE_KEY, orderNo),
                true,
                GlobalConstants.ONE,
                TimeUnit.DAYS);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayVO payOrder(PayDTO payDTO) {
        // 获取支付代理
        Optional<IPayProxyService> payServiceOptional = payDTO.getPayType().getPayService();
        if (!payServiceOptional.isPresent()) {
            throw new BusinessException("当前支付类型不支持");
        }


        // 获取订单详情
        OrderVO orderVO = orderFeignClientService.getOrderDetailByNo(payDTO.getOrderNo()).getData();
        if (Objects.isNull(orderVO)) {
            throw new BusinessException("当前订单信息有误");
        }

        // 免费支付
        if (NumberUtil.compare(orderVO.getAmount().longValue(), GlobalConstants.L_ZERO) == GlobalConstants.ZERO) {
            // 免费直接发放奖励
            this.grantPrize(orderVO.getNo(), orderVO.getGoodsNo());
            return PayVO.build().setPayStatus(PayStatusEnum.PAY_YES);
        }

        // debug模式，模拟支付成功
        if (PayConstants.DEBUG) {
            // 模拟支付回调
            this.payCallbackHandler(payDTO);
            return PayVO.build().setPayStatus(PayStatusEnum.PAY_YES);
        }

        // 支付结果
        return payServiceOptional.get().pay(payDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayVO payGoods(PayDTO payDTO) {
        log.info("用户：{} 开始进行商品：{} 支付", SecurityUtils.getAuthenticationUser().getId(), payDTO.getGoodsNo());
        // 商品支付，商品编号不能为空，商品数量默认为1
        if (StrUtil.isBlank(payDTO.getGoodsNo())) {
            throw new BusinessException("商品编码不能为空");
        }

        // 获取商品详情
        GoodsVO goodsVO = cmsFeignClientService.getGoodsDetailByNo(payDTO.getGoodsNo()).getData();
        if (Objects.isNull(goodsVO)) {
            throw new BusinessException("获取商品信息失败");
        }

        // 免费支付
        if (NumberUtil.compare(goodsVO.getSalesPrice().longValue(), GlobalConstants.L_ZERO) == GlobalConstants.ZERO) {
            this.grantPrize(null, goodsVO.getNo());
            // 免费直接发放奖励
            return PayVO.build().setPayStatus(PayStatusEnum.PAY_YES);
        }

        // 支付货币：非人民币支付
        if (CurrencyTypeEnum.CNY.getCode() != goodsVO.getCurrencyType()) {
            return this.otherPay(payDTO);
        }

        // 获取商品数量
        Integer goodsNum = Optional.ofNullable(payDTO.getGoodsNum()).orElse(GlobalConstants.ONE);
        OrderVO orderVo = orderFeignClientService.createOrder(OrderDTO.build().setGoodsNo(payDTO.getGoodsNo()).setGoodsNum(goodsNum)).getData();
        if (Objects.isNull(orderVo)) {
            throw new BusinessException("创建订单失败");
        }

        // 设置订单号
        payDTO.setOrderNo(orderVo.getNo());
        return this.payOrder(payDTO);
    }

    /**
     * 其他支付方式
     *
     * @param payDTO 支付数据
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.PayVO
     */
    private PayVO otherPay(PayDTO payDTO) {
        Optional<IPayProxyService> optionalPayProxyService = PayTypeEnum.VIRTUAL_PAY.getPayService();
        if (optionalPayProxyService.isPresent()) {
            return optionalPayProxyService.get().pay(payDTO);
        }

        return PayVO.build();
    }

    /**
     * 发放奖励
     *
     * @param orderNo 订单号
     * @param goodsNo 商品好
     */
    @Override
    public void grantPrize(String orderNo, String goodsNo) {
        // 发放商品
        GoodsVO goodsVO = cmsFeignClientService.getGoodsDetailByNo(goodsNo).getData();
        if (null == goodsVO) {
            throw new BusinessException("当前订单商品信息不存在");
        }

        // 如果当前订单商品为活动商品
        if (Objects.nonNull(goodsVO.getAction())) {
            Optional<ActionEnum> optionalActionEnum = ActionEnum.getInstanceByCode(goodsVO.getAction());
            if (optionalActionEnum.isPresent()) {
                ActionEnum actionEnum = optionalActionEnum.get();
                if (ActionEnum.DEFAULT != actionEnum) {
                    Object code = cmsFeignClientService.actionHandle(ActionDTO.build().setAction(actionEnum)
                            .setGoodsNo(goodsVO.getNo())
                            .setOrderNo(orderNo)).getCode();
                    // 返回状态不为0则表示失败
                    if (Objects.isNull(code) || GlobalConstants.ZERO != Integer.parseInt(code.toString())) {
                        log.error("用户：{} 订单：{} 奖励发放失败", SecurityUtils.getAuthenticationUser().getId(), orderNo);
                        throw new BusinessException("发放奖励失败");
                    }

                    // 只要商品为Action商品，则不直接发放奖励
                    return;
                }
            }
        }

        Integer goodsType = goodsVO.getGoodsType();
        Optional<GoodsTypeEnum> optionalGoodsTypeEnum = GoodsTypeEnum.getInstanceByCode(goodsType);
        if (!optionalGoodsTypeEnum.isPresent()) {
            throw new BusinessException("当前支付订单信息无效");
        }

        // 创建会员更新对象
        MemberDTO memberDTO = MemberDTO.build()
                .setOrigin(PropertyOriginEnum.BUY.getCode())
                .setOrderNo(orderNo);
        switch (optionalGoodsTypeEnum.get()) {
            case DIAMOND:
            case PROP_EXCHANGE:
                memberFeignClientService.updateMemberProperty(memberDTO.setDiamond(goodsVO.getDiamondNum().longValue())
                        .setCoupon(goodsVO.getCouponNum().longValue()));
                break;
            case GOLD_COIN:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsVO.getGoldCoinNum().longValue()));
                break;
            case COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setCoupon(goodsVO.getCouponNum().longValue()));
                break;
            case TABLECLOTH:
            case CARD_BACK:
                // 发放商品值背包中
                memberFeignClientService.insertGoodsInBackpack(goodsVO.getNo());
                break;
            case DIAMOND_COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setDiamond(goodsVO.getDiamondNum().longValue())
                        .setCoupon(goodsVO.getCouponNum().longValue()));
                break;
            case GOLD_COIN_COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsVO.getGoldCoinNum().longValue())
                        .setCoupon(goodsVO.getCouponNum().longValue()));
                break;
            case DIAMOND_GOLD_COIN:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsVO.getGoldCoinNum().longValue())
                        .setDiamond(goodsVO.getDiamondNum().longValue()));
                break;
            case GOLD_COIN_ALIVENESS:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsVO.getGoldCoinNum().longValue()));
                // 活跃度，当用户接收到支付成功后，在每日任务中，调用handle方法，将存储当天的活跃度
                break;
            case DIAMOND_GOLD_COIN_COUPON:
                memberFeignClientService.updateMemberProperty(memberDTO.setAmount(goodsVO.getGoldCoinNum().longValue())
                        .setDiamond(goodsVO.getDiamondNum().longValue())
                        .setCoupon(goodsVO.getCouponNum().longValue()));
                break;
            default:
        }
    }

    @Override
    public BillVO bill(BillDTO billDTO) {
        return null;
    }
}
