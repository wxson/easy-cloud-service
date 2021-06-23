package com.easy.cloud.web.component.core.service;

import com.easy.cloud.web.component.core.annotation.DefaultField;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 数据转换方法
 *
 * @author GR
 * @date 2020-11-4 15:53
 */
public interface IDefaultInitProxy {
    /**
     * 初始化默认值
     *
     * @param entity 传入对象
     */
    default <T> void initDefaultValue(T entity) {
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(DefaultField.class)) {
                    // 获取注解类
                    DefaultField fieldDefault = field.getAnnotation(DefaultField.class);
                    // 获取字段名称
                    String fieldName = field.getName();
                    // 构建BeanWrapper
                    BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
                    // 根据字段名称获取当前字段的值
                    Optional<Object> optional = Optional.ofNullable(beanWrapper.getPropertyValue(fieldName));
                    // 当前字段存在值，则忽略
                    if (optional.isPresent()) {
                        continue;
                    }

                    PropertyDescriptor propertyDescriptor = beanWrapper.getPropertyDescriptor(fieldName);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    // 当前为整形
                    if (propertyType.getName().equals(Integer.class.getName())) {
                        writeMethod.invoke(entity, fieldDefault.intValue());
                    }
                    // 当前为double
                    if (propertyType.getName().equals(Double.class.getName())) {
                        writeMethod.invoke(entity, fieldDefault.doubleValue());
                    }
                    // 当前为String
                    if (propertyType.getName().equals(String.class.getName())) {
                        writeMethod.invoke(entity, fieldDefault.strValue());
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
