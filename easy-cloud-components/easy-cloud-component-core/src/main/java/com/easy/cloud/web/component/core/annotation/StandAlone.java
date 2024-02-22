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
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface StandAlone {

    /**
     * 包名：用于校验是否是需要的目前对象
     *
     * @return
     */
    String packageName() default "";

    /**
     * 调用类名
     *
     * @return
     */
    String beanName();

    /**
     * 调用的方法
     *
     * @return
     */
    String invokeMethod();
}
