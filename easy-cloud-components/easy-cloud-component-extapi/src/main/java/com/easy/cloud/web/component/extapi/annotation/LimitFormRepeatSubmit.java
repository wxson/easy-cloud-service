package com.easy.cloud.web.component.extapi.annotation;

import java.lang.annotation.*;

/**
 * 表单重复提交注解
 *
 * @author GR
 * @date 2021-4-8 14:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitFormRepeatSubmit {
    /**
     * 默认前缀
     */
    String prefix() default "easy:cloud:limit:form:repeat:";

    /**
     * key
     */
    String value();

    /**
     * 读取第一个对象的字段名作为key
     */
    String readFirstObjectFiled() default "default";

    /**
     * 过期时间，默认十秒
     */
    int expireTime() default 10;
}
