package com.easy.cloud.web.oauth.configuration;

import com.easy.cloud.web.component.security.constants.SecurityConstants;
import com.easy.cloud.web.component.security.exception.SecurityWebResponseExceptionTranslator;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import com.easy.cloud.web.component.security.service.impl.SecurityClientDetailsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Oauth2 授权服务配置
 *
 * @author GR
 * @date 2021-3-29 14:20
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
@ConditionalOnExpression("${security.oauth2.jwt.enabled:false}")
public class JwtAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final AuthenticationManager authenticationManager;

    private final ISecurityUserDetailsService securityUserDetailsService;

    private final TokenStore jwtTokenStore;

    private final TokenEnhancer tokenEnhancer;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 授权服务配置,主要是为了颁发授权码
     *
     * @param clients 客户端信息
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        SecurityClientDetailsService clientDetailsService = new SecurityClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 开启/oauth/check_token验证端口认证权限访问,单点登录必须要配置
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 密码模式管理
     *
     * @param endpoints 端点控制
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // She之增强内容
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(tokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(delegates);

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(securityUserDetailsService)
                // 启用JWT 时打开注释
                .tokenStore(jwtTokenStore)
                // token 转换器
                .accessTokenConverter(jwtAccessTokenConverter)
                // jwt Token增强器
                .tokenEnhancer(tokenEnhancerChain)
                // 异常处理
                .exceptionTranslator(new SecurityWebResponseExceptionTranslator());
    }
}
