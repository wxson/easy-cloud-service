package com.easy.cloud.web.component.security.configuration;

import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security 配置
 *
 * @author GR
 * @date 2021-3-26 17:04
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@ComponentScan({"com.easy.cloud.web.component.security"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ISecurityUserDetailsService securityUserDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                // 放行规则
                .antMatchers("/oauth/**", "/login/**", "/logout/**")
                .permitAll()
                // 其他所有请求必须进行验证访问
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                // 支持跨域
                .cors()
                .and()
                // 关闭csrf,出于安全考虑，一般建议打开csrf，防止一些伪站点的攻击
                .csrf()
                .disable();
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
}
