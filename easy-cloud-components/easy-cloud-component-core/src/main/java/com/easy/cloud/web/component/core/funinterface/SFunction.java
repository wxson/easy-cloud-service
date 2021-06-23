package com.easy.cloud.web.component.core.funinterface;

import java.io.Serializable;

/**
 * 支持序列化的 Function
 *
 * @author GR
 * @date 2021-3-9 15:32
 */
@FunctionalInterface
public interface SFunction<T, R> extends Serializable {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
}