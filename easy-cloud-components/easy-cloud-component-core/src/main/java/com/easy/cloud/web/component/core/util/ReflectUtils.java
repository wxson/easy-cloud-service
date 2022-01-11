package com.easy.cloud.web.component.core.util;

import com.easy.cloud.web.component.core.exception.BusinessException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author GR
 * @date 2021-3-16 11:28
 */
public class ReflectUtils {

    /**
     * 反射获取类对象
     *
     * @param className 具体类包名
     * @return java.lang.Class<?>
     */
    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException exception) {
            throw new BusinessException(exception.getMessage());
        }
    }


    /**
     * 是否包含该属性
     *
     * @param properties 属性
     * @param entity     目标对象
     * @return java.lang.Boolean
     */
    public static <Entity> Boolean hasProperties(String properties, Entity entity) {
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
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
    public static <Entity, ReturnType> ReturnType getPropertiesValue(String properties, Entity entity) {
        // 是否存在属性
        if (ReflectUtils.hasProperties(properties, entity)) {
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
    public static <Entity> void setPropertiesValue(String properties, Object value, Entity entity) {
        // 如果存在属性
        if (ReflectUtils.hasProperties(properties, entity)) {
            try {
                // 构建BeanWrapper
                BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
                PropertyDescriptor propertyDescriptor = beanWrapper.getPropertyDescriptor(properties);
                // 获取属性值
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                Object propertiesValue = readMethod.invoke(entity);
                if (Objects.nonNull(propertiesValue)) {
                    return;
                }

                writeMethod.invoke(entity, value);
            } catch (IllegalAccessException | InvocationTargetException ignored) {

            }
        }
    }
}
