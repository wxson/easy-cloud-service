package com.easy.cloud.web.module.route.constants;

/**
 * 路由常量
 *
 * @author GR
 * @date 2021-3-26 11:29
 */
public class RouteConstants {
    /**
     * Redis路由模块公共前缀
     */
    public final static String MODULES_ROUTE_REDIS_PREFIX = "easy_cloud:modules:route:";

    /**
     * 网关路由信息发生改变通知KEY
     */
    public final static String MODULES_ROUTE_CHANGE_NOTICE_REDIS_KEY = MODULES_ROUTE_REDIS_PREFIX + "change:notice";

}
