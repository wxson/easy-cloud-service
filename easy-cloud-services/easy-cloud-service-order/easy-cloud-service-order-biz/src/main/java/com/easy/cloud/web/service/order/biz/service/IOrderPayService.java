package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;

/**
 * 订单支付逻辑
 *
 * @author GR
 * @date 2024/2/4 17:28
 */
public interface IOrderPayService {

    /**
     * 支付
     *
     * @param payRequestBody 支付请求参数
     * @return 支付请求响应数据
     */
    PayResponseBody pay(PayRequestBody payRequestBody);

}
