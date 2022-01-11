package com.easy.cloud.web.service.pay.biz.constants;

/**
 * Pay 公共常量
 *
 * @author GR
 * @date 2021-11-29 10:32
 */
public class PayConstants {
    /**
     * 是否开启debug模式
     */
    public static final Boolean DEBUG = true;
    /**
     * CMS redis 前缀
     */
    public static final String PAY_REDIS_PREFIX = "easy_cloud:pay:";
    /**
     * 订单回调通知
     * %s:%s  orderNo:true
     */
    public static final String ORDER_CALLBACK_NOTICE_KEY = PAY_REDIS_PREFIX + "order:callback:{}:{}";


}
