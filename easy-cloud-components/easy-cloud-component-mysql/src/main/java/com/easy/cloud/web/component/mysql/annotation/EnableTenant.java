package com.easy.cloud.web.component.mysql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 租户过滤器
 *
 * @author GR
 * @date 2023/11/10 10:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
public @interface EnableTenant {

  /**
   * 默认允许启用租户
   *
   * @return
   */
  boolean value() default true;
}
