package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
  /**
   * 菜单类型枚举
   */
  DIR(0, "目录"),
  MENU(1, "菜单"),
  BUTTON(2, "按钮"),
  ;

  private final int code;
  private final String desc;
}
