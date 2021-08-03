package com.easy.cloud.web.gateway.repository;

import com.easy.cloud.web.gateway.service.IDynamicRouteConfCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 使用Redis进行网关路由配置信息的存储
 *
 * @author GR
 * @date 2021-3-24 10:48
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    private final IDynamicRouteConfCacheService dynamicRouteConfCacheService;

    /**
     * 网关每隔30秒自动更新路由信息
     *
     * @return reactor.core.publisher.Flux<org.springframework.cloud.gateway.route.RouteDefinition>
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        // 每30秒自动获取一次
        return Flux.fromIterable(dynamicRouteConfCacheService.refreshDynamicRouteConf());
    }

    /**
     * Gateway内部提供的API
     *
     * @param route 保存的路由信息
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        // 不使用
        return route.flatMap(r -> Mono.empty());
    }

    /**
     * Gateway内部提供的API
     *
     * @param routeId 要删除的路由ID
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        // 不使用
        return Mono.empty();
    }
}
