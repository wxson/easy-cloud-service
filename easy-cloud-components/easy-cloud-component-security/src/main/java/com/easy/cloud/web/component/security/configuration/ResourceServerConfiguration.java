package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.core.constants.SwaggerApiConstants;
import com.easy.cloud.web.component.security.exception.SecurityResourceAuthExceptionEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Objects;

/**
 * Oauth2 资源服务配置
 *
 * @author GR
 * @date 2021-3-29 14:10
 */
@Slf4j
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        httpSecurity.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        permitAllUrlProperties.getIgnoreUrls()
                .forEach(url -> registry.antMatchers(url).permitAll());
        // 过滤swagger api
        registry.antMatchers(SwaggerApiConstants.API_URI).permitAll();
        // 剩余拦截
        registry.anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置JwtTokenStore
        this.setJwtTokenStore(resources);
        // 配置资源服务器认证异常返回结果
        resources.authenticationEntryPoint(new SecurityResourceAuthExceptionEntryPoint());
    }

    /**
     * 配置JwtTokenStore
     *
     * @param resources ResourceServerSecurityConfigurer
     */
    private void setJwtTokenStore(ResourceServerSecurityConfigurer resources) {
        if (Objects.nonNull(jwtAccessTokenConverter)) {
            resources.tokenStore(new JwtTokenStore(jwtAccessTokenConverter));
        }
    }
}
