package com.easy.cloud.web.component.pay.service;

import com.easy.cloud.web.component.pay.enums.PayTypeEnum;

/**
 * 支付工厂
 *
 * @author GR
 * @date 2024/2/4 15:34
 */
public interface IPayFactory {

    /**
     * 获取支付客户端
     *
     * @param payType 支付方式
     * @return 返回对应的支付客户端
     */
    IPayClientService getPayClient(PayTypeEnum payType);

}
