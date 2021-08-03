package com.easy.cloud.web.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * @author GR
 * @date 2021-3-23 16:53
 */
public interface IDynamicRouteConfCacheService {

    /**
     * 初始化动态路由配置
     */
    void initDynamicRouteConf();

    /**
     * 刷新动态路由配置，并返回当前路由信息
     *
     * @return java.util.List<org.springframework.cloud.gateway.route.RouteDefinition>
     */
    List<RouteDefinition> refreshDynamicRouteConf();

    /**
     * 清空动态路由配置
     */
    void clearDynamicRouteConf();
}
