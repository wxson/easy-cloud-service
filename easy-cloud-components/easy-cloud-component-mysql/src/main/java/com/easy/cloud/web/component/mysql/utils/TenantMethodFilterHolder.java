package com.easy.cloud.web.component.mysql.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * 存储滤掉不需要的租户方法
 * <p>一般租户的启用会在Class上全局注释，但因为业务逻辑导致某个方法中不能携带租户信息，故有此逻辑</p>
 * <p>比如：租户查询平台信息时，因为租户限制，导致无法查询的情况</p>
 *
 * @author GR
 * @date 2023/10/4
 */
@UtilityClass
public class TenantMethodFilterHolder {

  private final List<String> FILTER_TENANT_METHOD = new ArrayList<>();

  /**
   * 添加要过滤的方法
   *
   * @param declaredMethod 方法
   */
  public void addFilterTenantMethod(Method declaredMethod) {
    FILTER_TENANT_METHOD.add(buildUniqueMethodTag(declaredMethod));
  }

  public Boolean matchMethod(Method declaredMethod) {
    return FILTER_TENANT_METHOD.contains(buildUniqueMethodTag(declaredMethod));
  }

  /**
   * 构建唯一方法
   *
   * @param declaredMethod AOP 方法
   * @return
   */
  private String buildUniqueMethodTag(Method declaredMethod) {
    StringBuilder builder = new StringBuilder();
    builder.append(declaredMethod.getDeclaringClass().getName());
    builder.append(declaredMethod.getName());
    for (Class<?> parameterType : declaredMethod.getParameterTypes()) {
      // TODO 此处存在不完整性，若发现无法关闭租户功能，必要时需结合实际修改，比如同名方法，参数都是集合，集合内部的泛型不同
      builder.append(parameterType.getName());
    }
    return builder.toString();
  }
}
