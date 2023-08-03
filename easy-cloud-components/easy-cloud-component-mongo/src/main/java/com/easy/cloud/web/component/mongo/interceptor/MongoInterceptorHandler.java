package com.easy.cloud.web.component.mongo.interceptor;

import cn.hutool.core.date.DateUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

/**
 * <p>使用MongoDB拦截器，设置默认值，需在项目中自定义</p>
 *
 * @author GR
 * @date 2023/7/25 18:34
 */
@Slf4j
public class MongoInterceptorHandler implements BeforeConvertCallback<Object> {

  private final Map<String, String> entityIdFiled = new HashMap<>();

  private final MongoInterceptorProperty mongoInterceptorProperty;

  public MongoInterceptorHandler(MongoInterceptorProperty mongoInterceptorProperty) {
    this.mongoInterceptorProperty = mongoInterceptorProperty;
  }

  @Override
  public Object onBeforeConvert(Object targetDoc, String tdbTable) {
    if (Objects.isNull(mongoInterceptorProperty)) {
      return targetDoc;
    }
    try {
      // 获取当前登录用户
      Object authenticationUserId = this.readAuthenticationUserId();
      if (this.isNew(targetDoc)) {
        if (Objects.nonNull(authenticationUserId)) {
          // 设置创建用户
          this.setDefaultProperty(targetDoc, mongoInterceptorProperty.CREATOR_AT_FILED,
              authenticationUserId);
        }
        // 设置创建时间
        this.setDefaultProperty(targetDoc, mongoInterceptorProperty.CREATE_AT_FILED,
            DateUtil.now());
      } else {
        // 设置更新用户
        if (Objects.nonNull(authenticationUserId)) {
          // 设置更新用户
          this.setDefaultProperty(targetDoc, mongoInterceptorProperty.UPDATOR_AT_FILED,
              authenticationUserId);
        }
        // 设置更新时间
        this.setDefaultProperty(targetDoc, mongoInterceptorProperty.UPDATE_AT_FILED,
            DateUtil.now());
      }
    } catch (Exception exception) {
      log.info("MongoInterceptorHandler Exception：{}", exception.getMessage());
    }
    return targetDoc;
  }

  /**
   * 读取认证用户信息
   *
   * @return
   */
  public Object readAuthenticationUserId() {
    return null;
  }

  /**
   * 设置默认属性值
   *
   * @param targetDoc
   * @param filed
   */
  private void setDefaultProperty(Object targetDoc, String filed, Object value)
      throws NoSuchFieldException, IllegalAccessException {
    // 设置创建时间
    Field field = targetDoc.getClass().getDeclaredField(filed);
    field.setAccessible(true);
    field.set(targetDoc, value);
  }

  /**
   * 根据ID字段是否有值来判定是否为新对象
   *
   * @param targetDoc
   * @return
   */
  private Boolean isNew(Object targetDoc) {
    try {
      // 构建唯一对象标识
      String unique = targetDoc.getClass().getName();
      // 缓存中是否已包含ID字段名
      if (entityIdFiled.containsKey(unique)) {
        // 设置创建时间
        Field field = targetDoc.getClass().getDeclaredField(entityIdFiled.get(unique));
        field.setAccessible(true);
        return Objects.isNull(field.get(targetDoc));
      }
      // 遍历寻找ID字段
      for (Field declaredField : targetDoc.getClass().getDeclaredFields()) {
        Id id = declaredField.getAnnotation(Id.class);
        if (Objects.nonNull(id)) {
          declaredField.setAccessible(true);
          entityIdFiled.put(unique, declaredField.getName());
          return Objects.isNull(declaredField.get(targetDoc));
        }
      }
    } catch (Exception e) {
    }
    return false;
  }
}
