package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型枚举
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
  /**
   * 角色枚举类型
   */
  ROLE_SUPER_ADMIN("admin", "超级管理员"),
  ROLE_TENANT("TENANT", "租户"),
  ;

  private final String code;
  private final String desc;
}
