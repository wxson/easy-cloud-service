package com.easy.cloud.web.component.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作记录注解
 *
 * @author GR
 * @date 2020-11-18 10:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface OperationRecord {

    /**
     * 描述
     */
    String value();

    /**
     * 类型
     */
    Type type() default Type.INFO;

    /**
     * action
     */
    Action action();

    enum Type {
        /**
         * 操作记录类型
         */
        INFO,
        WARNING,
        DANGER,
        ERROR
    }

    enum Action {
        /**
         * 操作
         */
        ADD,
        DELETE,
        UPDATE,
        FIND,
        PAGE,
        LOGIN,
        LOGOUT
    }
}
