package com.easy.cloud.web.component.security.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.easy.cloud.web.component.security.service.IPermissionService;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

/**
 * 接口权限判断工具
 * <p>说明：</p>
 * <p>1、权限校验仅限于接口权限而非菜单权限</p>
 * <p>2、一个接口对应一个权限标志</p>
 * <p>3、一个权限标志对应一个按钮</p>
 * <p>4、故此处校验每一个权限标志方可</p>
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

    // 获取认证用户所拥有的的权限标识
    return SecurityUtils.getUserPermissions().stream()
        .anyMatch(permission -> PatternMatchUtils.simpleMatch(permissions, permission));
  }
}
