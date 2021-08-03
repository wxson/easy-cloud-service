package com.easy.cloud.web.gateway.constants;

/**
 * @author GR
 * @date 2021-3-23 17:12
 */
public class GatewayConstants {

    /**
     * Redis网关公共前缀
     */
    public final static String GATEWAY_REDIS_PREFIX = "easy_cloud:gateway:";

    /**
     * Redis网关公共前缀
     */
    public final static String GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY = GATEWAY_REDIS_PREFIX + "route:conf:cache";
}
