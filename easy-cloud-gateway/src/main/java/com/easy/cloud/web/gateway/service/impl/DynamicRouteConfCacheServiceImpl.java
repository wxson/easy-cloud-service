package com.easy.cloud.web.gateway.service.impl;

import com.easy.cloud.web.gateway.service.IDynamicRouteConfCacheService;
import com.easy.cloud.web.gateway.util.DynamicRouteConfCacheHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 动态路由配置信息
 *
 * @author GR
 * @date 2021-3-23 16:53
 */
@Slf4j
@Component
@AllArgsConstructor
public class DynamicRouteConfCacheServiceImpl implements IDynamicRouteConfCacheService {

    private final DynamicRouteConfCacheHolder dynamicRouteConfCacheHolder;

    @Override
    public void initDynamicRouteConf() {
        this.clearDynamicRouteConf();
        this.refreshDynamicRouteConf();
    }

    @Override
    public List<RouteDefinition> refreshDynamicRouteConf() {
        return null;
    }

    @Override
    public void clearDynamicRouteConf() {
        dynamicRouteConfCacheHolder.clearDynamicRouteConf();
    }
}
