package com.easy.cloud.web.component.security.util;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
   * 获取用户角色信息
   *
   * @return 角色集合
   */
  public Set<String> getUserPermissions() {
    if (true) {
      return CollUtil.newHashSet(
          "menu_add",
          "menu_edit",
          "menu_delete",
          "menu_query"
      );
    }
    return SecurityUtils.getAuthenticationUser()
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
  }
}
