package com.easy.cloud.web.component.security.service.impl;

import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 账号信息数据库校验配置
 *
 * @author GR
 * @date 2021-3-26 17:07
 */
@Component
@AllArgsConstructor
public class SecurityUserDetailsServiceImpl implements ISecurityUserDetailsService {

    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * Security过滤器通过调用loadUserByUsername方法获取用户详情，
     * WebSecurityConfigurerAdapter中指定密码的加密方式，Security自动校验
     * 校验成功，回调AuthenticationSuccessHandler，反之AuthenticationFailureHandler
     *
     * @param userName 账号
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 模拟测试权限
        if ("root".equals(userName)) {
            String password = bCryptPasswordEncoder.encode(userName);
            // Authority 即角色
            return new AuthenticationUser("100001", userName, password, "0",
                    true, true, true, true,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                    .setTenantId("测试租户ID_01");
        }

        String password = bCryptPasswordEncoder.encode(userName);
        // Authority 即角色
        return new AuthenticationUser("100002", userName, password, "1",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                .setTenantId("测试租户ID_02");
    }
}
