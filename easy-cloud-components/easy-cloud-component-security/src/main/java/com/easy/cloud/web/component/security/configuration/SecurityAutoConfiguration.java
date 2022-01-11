package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.constants.SecurityConstants;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.handler.SecurityAuthenticationFailureHandler;
import com.easy.cloud.web.component.security.handler.SecurityAuthenticationSuccessHandler;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import com.easy.cloud.web.component.security.service.impl.SecurityUserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author GR
 * @date 2021-3-26 16:48
 */
@ComponentScan("com.easy.cloud.web.component.security")
public class SecurityAutoConfiguration {

    /**
     * 配置token增强处理
     * 即：在获取token的请求中，需要额外添加其他的数据，则可在这里添加
     *
     * @return org.springframework.security.oauth2.provider.token.TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
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

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SecurityAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
        return new SecurityAuthenticationSuccessHandler();
    }

    /**
     * 配置密码的加密方式
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ISecurityUserDetailsService securityUserDetailsService() {
        return new SecurityUserDetailsServiceImpl();
    }
}
