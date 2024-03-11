package com.easy.cloud.web.module.netty.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;

/**
 * 是否允许Netty
 *
 * @author GR
 * @date 2023/5/17 11:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
//让此注解继承@Service注解，在项目启动时，自动扫描被ActionEndpoint注解的类
@Service
public @interface ActionEndpoint {

  String value() default "/";
}
