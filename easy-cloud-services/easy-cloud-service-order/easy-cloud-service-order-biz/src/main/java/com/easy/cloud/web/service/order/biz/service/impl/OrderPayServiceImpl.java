package com.easy.cloud.web.service.order.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.component.pay.service.IPayClientService;
import com.easy.cloud.web.component.pay.service.IPayFactory;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.service.IOrderPayService;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付逻辑
 *
 * @author GR
 * @date 2024/2/4 17:29
 */
@Slf4j
@Service
public class OrderPayServiceImpl implements IOrderPayService {

    @Autowired
    private IPayFactory payFactory;

    @Autowired
    private IOrderService orderService;

    @Override
    public PayResponseBody pay(PayRequestBody payRequestBody) {
        // 1、根据订单编号获取订单详情
        OrderVO orderVO = orderService.detailByNo(payRequestBody.getOrderNo());
        // 订单已失效，禁止支付
        if (OrderStatusEnum.INVALID == orderVO.getOrderStatus()) {
            throw new BusinessException("当前订单已失效，请重新下单");
        }
        // 设置订单支付数据
        payRequestBody.setGoodsName(orderVO.getGoodsName());
        payRequestBody.setAmount(orderVO.getAmount());
        payRequestBody.setPayType(payRequestBody.getPayType());
        payRequestBody.setOrderNo(payRequestBody.getOrderNo());

        // 2、获取支付类型对象
        IPayClientService payClient = payFactory.getPayClient(payRequestBody.getPayType());
        // 获取预支付数据
        PayResponseBody payResponseBody = payClient.pay(payRequestBody);

        // 订单已发起后端预支付逻辑，且预支付完成，等待支付成功回调
        orderService.prePayOrder(payRequestBody.getOrderNo());

        // 3、TODO ...
        return payResponseBody;
    }
}
