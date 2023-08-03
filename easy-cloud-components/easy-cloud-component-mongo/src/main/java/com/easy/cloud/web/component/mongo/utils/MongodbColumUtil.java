package com.easy.cloud.web.component.mongo.utils;


import com.easy.cloud.web.component.mongo.SFunction;
import com.easy.cloud.web.component.mongo.SerializedLambda;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;

/**
 * MongoDB操作获取表实体字段属性工具类
 *
 * @author GR
 * @date 2020-11-4 15:53
 */
@UtilityClass
public class MongodbColumUtil {

  /**
   * SerializedLambda 反序列化缓存
   */
  private static final Map<Class, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

  /**
   * 解析 lambda 表达式
   *
   * @param func 需要解析的 lambda 对象
   * @param <T>  类型，被调用的 Function 对象的目标类型
   * @return 返回解析后的结果
   */
  public static <T> SerializedLambda resolve(SFunction<T, ?> func) {
    Class clazz = func.getClass();
    return Optional.ofNullable(FUNC_CACHE.get(clazz))
        .map(WeakReference::get)
        .orElseGet(() -> {
          SerializedLambda lambda = SerializedLambda.resolve(func);
          FUNC_CACHE.put(clazz, new WeakReference<>(lambda));
          return lambda;
        });
  }

  /**
   * 获取MongoDB表实体字段属性名称
   *
   * @param func : 函数式接口
   * @param <T>  : 泛型标记
   * @return 返回表实体字段属性
   */
  public static <T> String getEntityClum(SFunction<T, ?> func) {
    SerializedLambda resolve = resolve(func);
    String implMethodName = resolve.getImplMethodName();
    return implMethodName.substring(3).substring(0, 1).toLowerCase() + implMethodName.substring(4);
  }
}
