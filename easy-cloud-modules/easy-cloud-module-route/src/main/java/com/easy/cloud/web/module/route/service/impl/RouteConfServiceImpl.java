package com.easy.cloud.web.module.route.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.gateway.constants.GatewayRouteConfConstants;
import com.easy.cloud.web.module.route.domain.RouteConf;
import com.easy.cloud.web.module.route.repository.RouteConfRepository;
import com.easy.cloud.web.module.route.service.RouteConfService;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class RouteConfServiceImpl implements RouteConfService {

  private final RedisTemplate redisTemplate;

  private final RouteConfRepository routeConfRepository;

  @Override
  public void init() {
    if (routeConfRepository.count() <= 0) {
      // 初始化角色数据
      List<RouteConf> routeConfList = this.initJsonToList("json/sys_route.json", RouteConf.class);
      // 初始化存储
      routeConfRepository.saveAll(routeConfList);
      log.info("init platform roles content success!");
    }
  }

  @Override
  public boolean save(RouteConf routeConf) {
    routeConfRepository.save(routeConf);
    return true;
  }

  @Override
  public boolean updateById(RouteConf routeConf) {
    routeConfRepository.save(routeConf);
    return true;
  }

  @Override
  public boolean removeById(String id) {
    routeConfRepository.deleteById(id);
    return true;
  }

  @Override
  public RouteConf getById(String id) {
    return routeConfRepository.findById(id).orElseThrow(() -> new BusinessException("当前路由信息不存在"));
  }

  @Override
  public List<RouteConf> list() {
    return routeConfRepository.findAll();
  }

  @Override
  public void sendRouteConfChangeNotice(RouteConf routeConf) {
    RouteDefinition routeDefinition = new RouteDefinition();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    if (Objects.nonNull(routeConf)) {
      routeDefinition.setId(routeConf.getRouteId());
      routeDefinition.setUri(URI.create(routeConf.getUri()));
      routeDefinition.setOrder(routeConf.getSort());

      JSONArray filterObj = JSONUtil.parseArray(routeConf.getFilters());
      routeDefinition.setFilters(filterObj.toList(FilterDefinition.class));
      JSONArray predicateObj = JSONUtil.parseArray(routeConf.getPredicates());
      routeDefinition.setPredicates(predicateObj.toList(PredicateDefinition.class));
      redisTemplate.opsForHash()
          .put(GatewayRouteConfConstants.GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY, routeConf.getRouteId(),
              routeDefinition);
    }

    redisTemplate.convertAndSend(GatewayRouteConfConstants.ROUTE_CHANGE_NOTICE_REDIS_TOPIC,
        "路由信息发生改变,网关缓存更新");
    log.info("redis 事件发送，路由信息发生改变：{}", JSONUtil.toJsonStr(routeDefinition));
  }
}
