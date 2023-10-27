package com.easy.cloud.web.component.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 *
 * @author GR
 * @date 2020-11-18 10:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SysLog {

  /**
   * 描述
   */
  String value();

  /**
   * action
   */
  Action action() default Action.NONE;

  enum Action {
    /**
     * 操作
     */
    NONE,
    ADD,
    DELETE,
    UPDATE,
    FIND,
    DOWNLOAD,
    UPLOAD
  }
}
