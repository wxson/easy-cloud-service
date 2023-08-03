package com.easy.cloud.web.component.mongo;

import java.io.Serializable;

/**
 * 支持序列化的 Function
 *
 * @author GR
 * @date 2020-11-4 15:53
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