package com.easy.cloud.web.component.gateway.configuration;

import com.easy.cloud.web.component.gateway.constants.GatewayRouteConfConstants;
import com.easy.cloud.web.component.gateway.util.DynamicRouteConfCacheHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author GR
 * @date 2021-3-23 16:49
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class GatewayAutoConfiguration {

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
            DynamicRouteConfCacheHolder.clearDynamicRouteConf();
        }, new ChannelTopic(GatewayRouteConfConstants.ROUTE_CHANGE_NOTICE_REDIS_TOPIC));
        return redisMessageListenerContainer;
    }
}
