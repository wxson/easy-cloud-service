package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限操作枚举 permission 包含：MenuPermission、ApplicationPermission、NavPermission、ApiPermission....
 *
 * @author GR
 * @date 2020-11-13 13:44
 */
@Getter
@AllArgsConstructor
public enum PermissionActionEnum {
  /**
   * 新增权限
   */
  ADD(1, "add", "", false, "新增权限"),
  /**
   * 删除权限
   */
  DELETE(2, "delete", "", true, "删除权限"),
  /**
   * 更新权限
   */
  EDIT(3, "edit", "", true, "编辑权限"),
  /**
   * 查看权限
   */
  LOOK(4, "look", "", false, "查看权限"),
  /**
   * 导入权限
   */
  IMPORT(5, "import", "", true, "导入权限"),
  /**
   * 导出权限
   */
  EXPORT(6, "export", "", true, "导出权限"),
  ;
  private final int code;
  /**
   * 字段
   */
  private final String filed;
  /**
   * 图标
   */
  private final String icon;
  /**
   * 默认状态，当前状态为true时，用户操作时需重新娇艳是否又该权限
   */
  private final Boolean defaultCheck;
  /**
   * 描述
   */
  private final String desc;
}
