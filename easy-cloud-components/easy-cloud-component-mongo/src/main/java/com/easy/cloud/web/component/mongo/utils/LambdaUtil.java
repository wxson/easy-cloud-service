package com.easy.cloud.web.component.mongo.utils;

import com.easy.cloud.web.component.mongo.SFunction;

/**
 * Lambda 工具
 *
 * @author GR
 * @date 2022/5/26 17:31
 */
public class LambdaUtil {

  /**
   * 获取字段属性
   *
   * @param func func
   * @return java.lang.String
   */
  public static <T> String of(SFunction<T, ?> func) {
    return MongodbColumUtil.getEntityClum(func);
  }
}
