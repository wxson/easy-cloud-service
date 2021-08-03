package com.easy.cloud.web.gateway.configuration;

import com.easy.cloud.web.gateway.service.IDynamicRouteConfCacheService;
import com.easy.cloud.web.module.route.constants.RouteConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;

/**
 * @author GR
 * @date 2021-3-23 16:49
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class GatewayAutoConfiguration {

    private final IDynamicRouteConfCacheService dynamicRouteConfService;

    @Async
    @EventListener(ApplicationPreparedEvent.class)
    public void initDynamicRouteCache() {
        // 初始化动态路由缓存信息
        log.info("----------初始化网关路由------------");
        dynamicRouteConfService.initDynamicRouteConf();
    }

    /**
     * redis 监听配置
     *
     * @param redisConnectionFactory redis 配置
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener((message, bytes) -> {
            log.info("----------监听到网关路由信息发生改变，进行重新加载路由------------");
            dynamicRouteConfService.clearDynamicRouteConf();
        }, new ChannelTopic(RouteConstants.MODULES_ROUTE_CHANGE_NOTICE_REDIS_KEY));
        return redisMessageListenerContainer;
    }
}
