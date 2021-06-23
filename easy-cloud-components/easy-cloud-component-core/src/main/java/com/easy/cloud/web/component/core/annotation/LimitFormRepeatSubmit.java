package com.easy.cloud.web.component.core.annotation;

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
}
