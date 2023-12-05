package com.easy.cloud.web.module.route.configuration;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.gateway.constants.GatewayRouteConfConstants;
import com.easy.cloud.web.module.route.service.RouteConfService;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;

/**
 * @author GR
 * @date 2021-3-9 17:59
 */
@Slf4j
@Configuration
@RefreshScope
@AllArgsConstructor
public class RouteAutoConfiguration {

  private final RedisTemplate redisTemplate;

  private final RouteConfService routeConfService;

  @Async
  @Order
  @EventListener({WebServerInitializedEvent.class})
  public void initRoute() {
    /**
     * 注：初始化顺序不能乱
     */
    // 初始化路由信息配置
    routeConfService.init();
    // 移除所有路由信息
    redisTemplate.delete(GatewayRouteConfConstants.GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY);
    // 读取数据库路由配置
    routeConfService.list().forEach(route -> {
      RouteDefinition routeDefinition = new RouteDefinition();
      routeDefinition.setId(route.getRouteId());
      routeDefinition.setUri(URI.create(route.getUri()));
      routeDefinition.setOrder(route.getSort());

      JSONArray filterObj = JSONUtil.parseArray(route.getFilters());
      routeDefinition.setFilters(filterObj.toList(FilterDefinition.class));
      JSONArray predicateObj = JSONUtil.parseArray(route.getPredicates());
      routeDefinition.setPredicates(predicateObj.toList(PredicateDefinition.class));

      log.info("初始化路由ID：{},{}", route.getRouteId(), routeDefinition);
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      redisTemplate
          .setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinition.class));
      redisTemplate.opsForHash()
          .put(GatewayRouteConfConstants.GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY, route.getRouteId(),
              routeDefinition);
    });
    // 通知路由重新加载
    routeConfService.sendRouteConfChangeNotice(null);
  }
}
