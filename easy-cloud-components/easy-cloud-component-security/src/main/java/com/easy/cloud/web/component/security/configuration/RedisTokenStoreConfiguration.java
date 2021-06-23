package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.constants.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置Token存储Redis
 * 使用时打开@Configuration注释
 *
 * @author GR
 * @date 2021-3-29 16:46
 */
//@Configuration
public class RedisTokenStoreConfiguration {

    @Bean
    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(SecurityConstants.COMPONENT_SECURITY_REDIS_TOKEN_PREFIX);
        return redisTokenStore;
    }
}
