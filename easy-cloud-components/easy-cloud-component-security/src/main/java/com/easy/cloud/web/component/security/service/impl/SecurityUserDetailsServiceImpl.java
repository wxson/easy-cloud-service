package com.easy.cloud.web.component.security.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.service.ISecurityUserDetailsService;
import com.easy.cloud.web.service.upms.api.domain.User;
import com.easy.cloud.web.service.upms.api.feign.UpmsFeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 账号信息数据库校验配置
 *
 * @author GR
 * @date 2021-3-26 17:07
 */
public class SecurityUserDetailsServiceImpl implements ISecurityUserDetailsService {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Lazy
    @Autowired
    private UpmsFeignClientService upmsFeignClientService;

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
        HttpResult<User> httpResult = upmsFeignClientService.loadUserByUsername(userName);
        User user = httpResult.getData();
        if (null == user) {
            throw new UsernameNotFoundException("当前账号用户不存在");
        }

        // Authority 即角色
        return new AuthenticationUser(user.getId(), user.getAccount(), user.getPassword(), user.getTenantId(),
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public UserDetails loadUserBySocial(String principal) {
        // 解析requestBodyStr
        JSONObject jsonObject = JSONUtil.parseObj(principal);
        String type = jsonObject.getStr("type");
        if (StrUtil.isBlank(type)) {
            throw new UsernameNotFoundException("当前登录类型不能为空");
        }

        // 获取用户信息
        User user = upmsFeignClientService.loadSocialUser(type, jsonObject).getData();
        if (null == user) {
            throw new UsernameNotFoundException("当前账号用户不存在");
        }

        // Authority 即角色
        return new AuthenticationUser(user.getId(), user.getAccount(), "N/A", user.getTenantId(),
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
