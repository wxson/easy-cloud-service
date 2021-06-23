package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.configuration.converter.SecurityUserAuthenticationConverter;
import com.easy.cloud.web.component.security.constants.SecurityConstants;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 配置Token转JwtToken
 *
 * @author GR
 * @date 2021-3-29 16:46
 */
@Configuration
@AllArgsConstructor
public class JwtTokenStoreConfiguration {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return (oAuth2AccessToken, authentication) -> {
            Map<String, Object> hashMap = new HashMap<>();
            Authentication userAuthentication = authentication.getUserAuthentication();
            Object principal = userAuthentication.getPrincipal();
            if (Objects.nonNull(principal) && principal instanceof AuthenticationUser) {
                AuthenticationUser authenticationUser = (AuthenticationUser) principal;
                hashMap.put(SecurityConstants.AUTHORIZATION_USER_ID, authenticationUser.getId());
                hashMap.put(SecurityConstants.AUTHORIZATION_USER_CHANEL_ID, authenticationUser.getChannelId());
                hashMap.put(SecurityConstants.AUTHORIZATION_USER_TENANT_ID, authenticationUser.getTenantId());
            }

            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(hashMap);
            return oAuth2AccessToken;
        };
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
