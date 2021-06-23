package com.easy.cloud.web.component.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段默认值
 *
 * @author GR
 * @date 2019/11/27 15:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DefaultField {

    int intValue() default 0;

    double doubleValue() default 0.00;

    String strValue() default "";
}
