package com.easy.cloud.web.component.core.service;

import com.easy.cloud.web.component.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 数据转换方法
 *
 * @author GR
 * @date 2020-11-4 15:53
 */
public interface IConvertProxy extends IDefaultInitProxy {
    /**
     * 当前对象转为目标对象
     *
     * @param toClass          目标对象
     * @param ignoreProperties 过滤的熟悉
     * @return TO
     */
    default <TO> TO convertTo(Class<TO> toClass, String... ignoreProperties) {
        try {
            Constructor<TO> declaredConstructor = toClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            TO instance = declaredConstructor.newInstance();
            BeanUtils.copyProperties(this, instance, ignoreProperties);
            this.initDefaultValue(instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BusinessException("IConverter.convertTo() 数据转换异常：" + e.getMessage());
        }
    }

    /**
     * 复制属性值，从元数据中复制属性到改对象
     *
     * @param source 元数据
     */
    default void copyProperties(Object source) {
        try {
            for (Field field : source.getClass().getDeclaredFields()) {
                // 获取字段名称
                String fieldName = field.getName();
                // 是否含有属性
                if (this.hasProperties(fieldName)) {
                    Object propertiesValue = this.getPropertiesValue(source, fieldName);
                    this.setPropertiesValue(fieldName, propertiesValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 是否包含该属性
     *
     * @param properties 属性
     * @return java.lang.Boolean
     */
    default Boolean hasProperties(String properties) {
        for (Field declaredField : this.getClass().getDeclaredFields()) {
            if (declaredField.getName().equals(properties)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取属性值
     *
     * @param entity     目标对象
     * @param properties 属性
     * @return java.lang.Object
     */
    default <Entity, ReturnType> ReturnType getPropertiesValue(Entity entity, String properties) {
        // 是否存在属性
        if (this.hasProperties(properties)) {
            try {
                // 构建BeanWrapper
                BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
                PropertyDescriptor propertyDescriptor = beanWrapper.getPropertyDescriptor(properties);
                // 获取属性值
                Method readMethod = propertyDescriptor.getReadMethod();
                return (ReturnType) readMethod.invoke(entity);
            } catch (IllegalAccessException | InvocationTargetException ignored) {

            }
        }

        return null;
    }

    /**
     * 设置属性值
     *
     * @param properties 目标属性
     * @param value      目标属性值
     */
    default void setPropertiesValue(String properties, Object value) {
        // 如果存在属性
        if (this.hasProperties(properties)) {
            try {
                // 构建BeanWrapper
                BeanWrapper beanWrapper = new BeanWrapperImpl(this);
                PropertyDescriptor propertyDescriptor = beanWrapper.getPropertyDescriptor(properties);
                // 获取属性值
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Object propertiesValue = readMethod.invoke(this);
                if (Objects.nonNull(propertiesValue)) {
                    return;
                }

                writeMethod.invoke(this, value);
            } catch (IllegalAccessException | InvocationTargetException ignored) {

            }
        }
    }
}
