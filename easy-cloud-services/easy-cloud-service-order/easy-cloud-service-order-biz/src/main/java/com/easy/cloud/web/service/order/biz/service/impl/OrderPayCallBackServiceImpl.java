package com.easy.cloud.web.service.order.biz.service.impl;

import com.easy.cloud.web.service.order.api.dto.AliPayCallBackDTO;
import com.easy.cloud.web.service.order.api.dto.WxPayCallBackDTO;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.constants.OrderConstants;
import com.easy.cloud.web.service.order.biz.service.IOrderPayCallBackService;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 订单回调逻辑
 *
 * @author GR
 * @date 2021-11-27 10:13
 */
@Slf4j
@Service
public class OrderPayCallBackServiceImpl implements IOrderPayCallBackService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void wxPayCallBack(WxPayCallBackDTO wxPayCallBackDTO) {
        log.info("接收到微信支付订单：{} 回调信息：{}", wxPayCallBackDTO.getOutTradeNo(), wxPayCallBackDTO);
        // 获取订单信息
        OrderVO orderVO = orderService.detailByNo(wxPayCallBackDTO.getOutTradeNo());
        if (Objects.isNull(orderVO)) {
            log.error("接收到微信支付订单：{} 回调信息,获取订单信息为空", wxPayCallBackDTO.getOutTradeNo());
            return;
        }

        // 订单存在且当前订单状态为已预付，则支付回调有效
        if (OrderStatusEnum.PRE_PAID != orderVO.getOrderStatus()) {
            log.warn("接收到微信支付订单：{} 回调信息重复,当前订单状态：{}，", wxPayCallBackDTO.getOutTradeNo(), orderVO.getOrderStatus());
            return;
        }

        // 支付成功，移除当前订单过期倒计时
        String orderPayExpiredKey = OrderConstants.ORDER_PAY_EXPIRED_KEY + orderVO.getNo();
        redisTemplate.delete(orderPayExpiredKey);

        orderService.paySuccessHandler(wxPayCallBackDTO.getOutTradeNo(), wxPayCallBackDTO.getTransactionId());
    }

    @Override
    public void aliPayCallBack(AliPayCallBackDTO aliPayCallBackDTO) {

    }
}
