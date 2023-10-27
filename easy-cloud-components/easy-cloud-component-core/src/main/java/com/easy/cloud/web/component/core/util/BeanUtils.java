package com.easy.cloud.web.component.core.util;

import cn.hutool.core.util.StrUtil;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 是否使用BeanUtils，很简单，你的数量有上百万吗？你的系统接受不了几十毫秒的延时吗？
 *
 * @author GR
 * @date 2023/10/26 16:02
 */
@UtilityClass
public class BeanUtils {

  /**
   * 属性复制
   *
   * @param source
   * @param target
   */
  public void copyProperties(Object source, Object target) {
    org.springframework.beans.BeanUtils.copyProperties(source, target);
  }

  /**
   * 属性复制
   *
   * @param source
   * @param target
   * @param ignoreProperty 过滤空属性字段
   */
  public void copyProperties(Object source, Object target, boolean ignoreProperty) {
    if (ignoreProperty) {
      org.springframework.beans.BeanUtils
          .copyProperties(source, target, getEmptyProperties(source));
    } else {
      org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
  }

  /**
   * 获取空属性字段
   *
   * @param source
   * @return
   */
  public String[] getEmptyProperties(Object source) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(source);
    PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
    Set<String> emptyProperties = new HashSet<>(propertyDescriptors.length);
    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
      Object value = beanWrapper.getPropertyValue(propertyDescriptor.getName());
      if (Objects.nonNull(value) && StrUtil.isNotBlank(value.toString())) {
        continue;
      }
      emptyProperties.add(propertyDescriptor.getName());
    }
    return emptyProperties.toArray(new String[emptyProperties.size()]);
  }
}
