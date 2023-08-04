package com.easy.cloud.web.component.security.service;

/**
 * @author GR
 * @date 2023/8/3 16:05
 */
public interface IPermissionService {

  /**
   * 是否包含权限
   *
   * @param permissions 权限标识
   * @return
   */
  boolean hasPermission(String... permissions);
}
