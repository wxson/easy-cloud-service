package com.easy.cloud.web.component.extapi.annotation;

import java.lang.annotation.*;

/**
 * 分布式锁注解
 *
 * @author GR
 * @date 2021-4-8 14:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributionLock {
}
