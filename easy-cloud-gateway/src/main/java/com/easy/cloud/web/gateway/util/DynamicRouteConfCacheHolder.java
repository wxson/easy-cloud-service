package com.easy.cloud.web.gateway.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态路由配置缓存
 *
 * @author GR
 * @date 2021-3-24 11:11
 */
@Component
public class DynamicRouteConfCacheHolder {

    /**
     * key 路由ID
     * value 具体的路由信息
     */
    private final Cache<String, RouteDefinition> routeConfCache = CacheUtil.newLFUCache(50);

    /**
     * 从缓存中获取路由信息
     *
     * @return java.util.List<org.springframework.cloud.gateway.route.RouteDefinition>
     */
    public List<RouteDefinition> getDynamicRouteConfList() {
        return new ArrayList<>(CollUtil.newArrayList(routeConfCache.iterator()));
    }

    /**
     * 设置路由信息
     *
     * @param routeDefinition 设置的路由信息
     */
    public void putDynamicRouteConf(RouteDefinition routeDefinition) {
        routeConfCache.put(routeDefinition.getId(), routeDefinition);
    }

    /**
     * 设置路由信息
     *
     * @param list 设置的路由信息列表
     */
    public void putDynamicRouteConf(List<RouteDefinition> list) {
        list.forEach(routeDefinition -> routeConfCache.put(routeDefinition.getId(), routeDefinition));
    }

    /**
     * 清空缓存的路由信息
     */
    public void clearDynamicRouteConf() {
        routeConfCache.clear();
    }
}
