package com.easy.cloud.web.component.security.annotation;

import java.lang.annotation.*;

/**
 * 是否是内部访问
 *
 * @author GR
 * @date 2021-3-26 16:48
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {

    /**
     * 是否AOP统一处理
     *
     * @return false, true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段(预留)
     *
     * @return {}
     */
    String[] field() default {};
}
