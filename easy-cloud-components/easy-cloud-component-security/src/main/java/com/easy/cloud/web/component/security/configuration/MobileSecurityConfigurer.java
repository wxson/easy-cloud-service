package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.exception.SecurityResourceAuthExceptionEntryPoint;
import com.easy.cloud.web.component.security.mobile.MobileAuthenticationFilter;
import com.easy.cloud.web.component.security.mobile.MobileAuthenticationProvider;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置
 *
 * @author GR
 * @date 2021-3-26 17:04
 */
public class MobileSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private ISecurityUserDetailsService securityUserDetailsService;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationEventPublisher authenticationEventPublisher;

    @Override
    public void configure(HttpSecurity http) {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 必须配置authenticationSuccessHandler项，否则容易出现未知错误，难以排查
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        mobileAuthenticationFilter.setAuthenticationEventPublisher(authenticationEventPublisher);
        mobileAuthenticationFilter.setAuthenticationEntryPoint(new SecurityResourceAuthExceptionEntryPoint());

        // 在Security 过滤器链路中的UsernamePasswordAuthenticationFilter之后加入自定义过滤器
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider(securityUserDetailsService);
        http.authenticationProvider(mobileAuthenticationProvider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
