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
  ROLE_SUPER_ADMIN(1, "admin", "超级管理员"),
  ROLE_TENANT(2, "tenant", "租户"),
  ;

  private final long id;
  private final String code;
  private final String desc;
}
