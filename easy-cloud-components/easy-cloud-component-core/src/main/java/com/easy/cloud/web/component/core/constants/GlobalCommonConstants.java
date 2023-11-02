package com.easy.cloud.web.component.core.constants;

/**
 * 全局公共常量
 *
 * @author GR
 * @date 2021-3-31 10:45
 */
public class GlobalCommonConstants {

  /**
   * 租户字段
   */
  public static final String TENANT_ID_FIELD = "tenant_id";

  /**
   * 默认租户ID
   */
  public static final String DEFAULT_TENANT_ID_VALUE = "1";

  /**
   * 常用整型
   */
  public static final int SIGN_ONE = -1;
  public static final int ZERO = 0;
  public static final long L_ZERO = 0;
  public static final double D_ZERO = 0.0F;
  public static final float F_ZERO = 0.0F;
  public static final int ONE = 1;
  public static final int EIGHT = 8;
  public static final int TEN = 10;
  public static final int TWELVE = 12;

  /**
   * 全局Redis前缀
   */
  public static final String REDIS_PREFIX_KEY = "easy_cloud:";

  /**
   * 超管角色: 超级管理员获得所有权限
   */
  public static final String SUPER_ADMIN_ROLE = "admin";

  /**
   * 超管信息
   */
  public static final String TENANT_ROLE = "tenant";

  /**
   * 菜单树根目录ID
   */
  public static final String MENU_TREE_ROOT_ID = "0";

  /**
   * 部门树根目录ID
   */
  public static final String DEPART_TREE_ROOT_ID = "0";
}
