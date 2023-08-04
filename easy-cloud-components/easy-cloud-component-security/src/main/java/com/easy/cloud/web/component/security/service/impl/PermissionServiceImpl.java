package com.easy.cloud.web.component.security.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.easy.cloud.web.component.security.service.IPermissionService;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

/**
 * 接口权限判断工具
 *
 * @author GR
 * @date 2023/2/1
 */
@Slf4j
@Service("pms")
public class PermissionServiceImpl implements IPermissionService {

  /**
   * 判断接口是否有任意xxx，xxx权限
   *
   * @param permissions 权限
   * @return {boolean}
   */
  @Override
  public boolean hasPermission(String... permissions) {
    // 权限标识为空，则无权限
    if (ArrayUtil.isEmpty(permissions)) {
      return false;
    }
    // 获取认证用户
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return false;
    }
    // 获取认证用户所拥有的的权限标识
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    return authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .filter(StringUtils::hasText)
        .anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
  }
}
