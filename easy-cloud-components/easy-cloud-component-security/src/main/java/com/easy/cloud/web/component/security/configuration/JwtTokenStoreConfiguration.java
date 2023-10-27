package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.core.constants.SecurityConstants;
import com.easy.cloud.web.component.security.configuration.converter.SecurityUserAuthenticationConverter;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 配置Token转JwtToken
 * 默认关闭JWT模式，要想使用JWT时，需在配置文件中配置security.oauth2.jwt.enabled属性，默认false
 *
 * @author GR
 * @date 2021-3-29 16:46
 */
@Configuration
@AllArgsConstructor
@ConditionalOnExpression("${security.oauth2.jwt.enabled:false}")
public class JwtTokenStoreConfiguration {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    /**
     * Token字符串转JWtToken字符串转换器
     *
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        /* 注：不适用DefaultUserAuthenticationConverter该方法解析JwtToken信息至Principal对象中的原因是，此方法配置会是每一次请求都进入loadUserByUsername方法中,获取校验结果对象，已经违背了jwt无状态的初衷*/
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        UserAuthenticationConverter userAuthenticationConverter = new SecurityUserAuthenticationConverter();
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        // 设置jwt密钥
        jwtAccessTokenConverter.setSigningKey(SecurityConstants.JWT_TOKEN_SIGNING_KEY);
        return jwtAccessTokenConverter;
    }

}
