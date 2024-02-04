package com.easy.cloud.web.component.pay.service;

import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.component.pay.enums.PayTypeEnum;

/**
 * 支付逻辑
 *
 * @author GR
 * @date 2024/2/4 15:34
 */
public interface IPayClientService {

    /**
     * 支付；类型
     *
     * @return 返回支付类型
     */
    PayTypeEnum type();

    /**
     * 支付接口
     *
     * @param payRequestBody 支付请求数据
     * @return 支付结果
     */
    PayResponseBody pay(PayRequestBody payRequestBody);
}
