package com.easy.cloud.web.component.gateway.repository;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.gateway.constants.GatewayRouteConfConstants;
import com.easy.cloud.web.component.gateway.util.DynamicRouteConfCacheHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    private final RedisTemplate redisTemplate;

    /**
     * 网关每隔30秒自动更新路由信息
     *
     * @return reactor.core.publisher.Flux<org.springframework.cloud.gateway.route.RouteDefinition>
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        // 尝试从本地缓存中读取
        List<RouteDefinition> dynamicRouteConfList = DynamicRouteConfCacheHolder.getDynamicRouteConfList();
        if (CollUtil.isNotEmpty(dynamicRouteConfList)) {
            return Flux.fromIterable(dynamicRouteConfList);
        }

        // 尝试从Redis缓存中读取
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinition.class));
        List<RouteDefinition> routeDefinitionList = redisTemplate.opsForHash().values(GatewayRouteConfConstants.GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY);
        log.debug("读取Redis中的路由数据： {}， {}", routeDefinitionList.size(), routeDefinitionList);
        // 写入本地缓存中
        DynamicRouteConfCacheHolder.setDynamicRouteConf(routeDefinitionList);
        // 每30秒自动获取一次
        return Flux.fromIterable(routeDefinitionList);
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
