package com.easy.cloud.web.service.upms.biz.constant;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;

/**
 * 常量
 *
 * @author GR
 * @date 2021-11-11 10:13
 */
public interface UpmsCacheConstants {

  /**
   * upms服务缓存前缀
   */
  String UPMS_PREFIX = GlobalCommonConstants.REDIS_PREFIX_KEY + "upms:";

  /**
   * 菜单信息缓存
   */
  String MENU_DETAILS = UPMS_PREFIX + "menu_details";

  /**
   * 用户信息缓存
   */
  String USER_DETAILS = UPMS_PREFIX + "user_details";

  /**
   * 字典信息缓存
   */
  String DICT_DETAILS = UPMS_PREFIX + "dict_details";

  /**
   * 角色信息缓存
   */
  String ROLE_DETAILS = UPMS_PREFIX + "role_details";

  /**
   * 超管角色信息缓存
   */
  String SUPER_ROLE_DETAILS = UPMS_PREFIX + "role_details";
}
