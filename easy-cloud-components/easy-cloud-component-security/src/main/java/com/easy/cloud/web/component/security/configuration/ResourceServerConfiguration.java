package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.exception.SecurityResourceAuthExceptionEntryPoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Oauth2 资源服务配置
 *
 * @author GR
 * @date 2021-3-29 14:10
 */
@Slf4j
@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 所有请求都进行拦截
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                // 匹配所有资源
                .antMatchers("/**");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置Jwt Token解析，也可配置RemoteServices远程验证Token
        resources.tokenStore(new JwtTokenStore(jwtAccessTokenConverter))
                // 配置资源服务器认证异常返回结果
                .authenticationEntryPoint(new SecurityResourceAuthExceptionEntryPoint());
    }
}
