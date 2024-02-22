package com.easy.cloud.web.service.order.biz.constants;

/**
 * 订单常量
 *
 * @author GR
 * @date 2024/2/5 11:38
 */
public class OrderConstants {
    /**
     * Order redis 前缀
     */
    public static final String ORDER_REDIS_PREFIX = "easy_cloud:order:";
    /**
     * 订单回调通知
     * %s:%s  orderNo:true
     */
    public static final String ORDER_CALLBACK_NOTICE_KEY = ORDER_REDIS_PREFIX + "callback:{}";
    /**
     * 订单回调通知
     * %s:%s  orderNo:true
     */
    public static final String ORDER_NO_INCR_ID_KEY = ORDER_REDIS_PREFIX + "no:incr:{}";
    /**
     * 订单回调通知
     * %s:%s  orderNo:true
     */
    public static final String ORDER_PAY_EXPIRED_KEY = ORDER_REDIS_PREFIX + "pay:expired:";
}
