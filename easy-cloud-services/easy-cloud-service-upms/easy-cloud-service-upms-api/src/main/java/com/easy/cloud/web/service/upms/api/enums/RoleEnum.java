package com.easy.cloud.web.service.upms.api.enums;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
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
  ROLE_SUPER_ADMIN(GlobalCommonConstants.SUPER_ADMIN_ROLE, "超级管理员"),
  ROLE_TENANT(GlobalCommonConstants.TENANT_ROLE, "租户"),
  ;

  private final String code;
  private final String desc;
}
