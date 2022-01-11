package com.easy.cloud.web.service.order.biz.annotation;

import com.easy.cloud.web.service.order.biz.enums.PayTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author GR
 * @date 2021-11-27 10:32
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PayCallBackSignature {
    /**
     * 支付方式，默认微信支付
     */
    PayTypeEnum payType();
}
