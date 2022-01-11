package com.easy.cloud.web.oauth.configuration;

import com.easy.cloud.web.component.security.configuration.MobileSecurityConfigurer;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security 配置
 *
 * @author GR
 * @date 2021-3-26 17:04
 */
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ISecurityUserDetailsService securityUserDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .anyRequest().authenticated()
                // 支持跨域
                .and().cors()
                // 关闭csrf,出于安全考虑，一般建议打开csrf，防止一些伪站点的攻击
                .and().csrf().disable()
                .apply(mobileSecurityConfigurer());
    }

    /**
     * 加载用户详情服务类
     *
     * @param authenticationManagerBuilder 用户管理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // 指派账号详情对象以及密码加密对象，方便Security自动校验
        authenticationManagerBuilder.userDetailsService(securityUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public MobileSecurityConfigurer mobileSecurityConfigurer() {
        return new MobileSecurityConfigurer();
    }
}
