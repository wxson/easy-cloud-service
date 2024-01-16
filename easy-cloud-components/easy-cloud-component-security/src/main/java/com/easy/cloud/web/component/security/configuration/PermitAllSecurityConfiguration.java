package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.filter.PermitAllSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * 避免开放的API继续校验Token
 *
 * @author GR
 * @date 2024/1/16 11:04
 */
@Configuration
public class PermitAllSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PermitAllSecurityFilter permitAllSecurityFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(permitAllSecurityFilter, OAuth2AuthenticationProcessingFilter.class);
    }
}
