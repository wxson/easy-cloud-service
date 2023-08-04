package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统标志枚举
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
public enum SysFlagEnum {
  /**
   * 角色枚举类型
   */
  SYSTEM(0, "系统"),
  OTHER(1, "非系统"),
  ;

  private final int code;
  private final String desc;
}
