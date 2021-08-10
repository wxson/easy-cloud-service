package com.easy.cloud.web.module.route.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.gateway.constants.GatewayRouteConfConstants;
import com.easy.cloud.web.module.route.domain.RouteConf;
import com.easy.cloud.web.module.route.mapper.RouteConfMapper;
import com.easy.cloud.web.module.route.service.RouteConfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author GR
 */
@Slf4j
@Service
@AllArgsConstructor
public class RouteConfServiceImpl extends ServiceImpl<RouteConfMapper, RouteConf> implements RouteConfService {

    private final RedisTemplate redisTemplate;

    @Override
    public void sendRouteConfChangeNotice(RouteConf routeConf) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        if (Objects.nonNull(routeConf)) {
            redisTemplate.opsForHash().put(GatewayRouteConfConstants.GATEWAY_ROUTE_CONF_CACHE_REDIS_KEY, routeConf.getRouteId(), routeConf);
        }

        redisTemplate.convertAndSend(GatewayRouteConfConstants.ROUTE_CHANGE_NOTICE_REDIS_TOPIC, "路由信息发生改变,网关缓存更新");
        log.info("redis 事件发送，路由信息发生改变：{}", JSONUtil.toJsonStr(routeConf));
    }
}
