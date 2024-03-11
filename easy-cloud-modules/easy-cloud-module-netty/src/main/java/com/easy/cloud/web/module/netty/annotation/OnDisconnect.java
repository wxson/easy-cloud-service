package com.easy.cloud.web.module.netty.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 断线
 *
 * @author GR
 * @date 2023/5/17 11:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnDisconnect {

  String value() default "";
}
