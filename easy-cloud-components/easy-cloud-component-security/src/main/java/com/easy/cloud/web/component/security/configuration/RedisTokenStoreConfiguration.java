package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.constants.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置Token存储Redis
 * 默认使用Redis模式，要切换JWT，配置security.oauth2.jwt.enabled=true
 *
 * @author GR
 * @date 2021-3-29 16:46
 */
@Configuration
@AllArgsConstructor
@ConditionalOnExpression("!${security.oauth2.jwt.enabled:false}")
public class RedisTokenStoreConfiguration {

    @Bean
    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(SecurityConstants.COMPONENT_SECURITY_REDIS_TOKEN_PREFIX);
        redisTokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                // 可自定义存储key
                return super.extractKey(authentication);
            }
        });
        return redisTokenStore;
    }
}
