package com.easy.cloud.web.component.security.util;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @author GR
 * @date 2021-4-1 9:22
 */
@UtilityClass
public class SecurityUtils {
    /**
     * 获取Authentication
     *
     * @return org.springframework.security.core.Authentication
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取认证用户信息
     *
     * @return com.easy.cloud.component.security.domain.AuthenticationUser
     */
    public AuthenticationUser getAuthenticationUser() {
        Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new BusinessException("user not authentication");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthenticationUser) {
            return (AuthenticationUser) principal;
        }

        throw new BusinessException("Principal Object is not AuthenticationUser Class");
    }

    /**
     * 初始化系统默认用户
     */
    public void initSystemDefaultUser() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new AuthenticationUser(
                "system_default",
                "system_default",
                "N/A",
                "system_default",
                true, true, true, true, CollUtil.newArrayList()
        ), "N/A", CollUtil.newArrayList()));
    }
}
