package com.easy.cloud.web.component.core.annotation;

import java.lang.annotation.*;

/**
 * 频繁请求限制注解
 *
 * @author GR
 * @date 2021-4-8 16:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitFrequentRequest {
}
