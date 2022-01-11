package com.easy.cloud.web.component.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author GR
 * @date 2021-3-26 17:06
 */
public interface ISecurityUserDetailsService extends UserDetailsService {
    /**
     * 授权登录
     *
     * @param principal 认证
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserDetails loadUserBySocial(String principal);
}
