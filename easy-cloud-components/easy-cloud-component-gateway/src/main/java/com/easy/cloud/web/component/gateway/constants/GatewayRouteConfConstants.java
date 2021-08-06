package com.easy.cloud.web.component.gateway.constants;

/**
 * @author GR
 * @date 2021-3-23 17:12
 */
public class GatewayRouteConfConstants {

    /**
     * Redis网关公共前缀
     */
    public final static String GATEWAY_REDIS_PREFIX = "easy_cloud:gateway:";

    /**
     * Redis网关公共前缀
     */
    public final static String GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY = GATEWAY_REDIS_PREFIX + "route:conf:cache";

    /**
     * 网关路由信息发生改变通知KEY
     */
    public final static String ROUTE_CHANGE_NOTICE_REDIS_TOPIC = GATEWAY_REDIS_PREFIX + "route:change:notice";
}
