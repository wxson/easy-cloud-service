package com.easy.cloud.web.component.mysql.service;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 属性赋值
 *
 * @author GR
 * @date 2020-11-11 11:07
 */
public interface IPropertyAssignmentHandler {

    /**
     * 默认创建信息赋值
     *
     * @param entity 创建的信息
     */
    default <Entity> void saveDefaultAssignment(Entity entity) {
        // 设置默认属性值
//        this.setPropertiesValue(entity, "createTime", DateUtil.date());
    }

    /**
     * 批量默认创建信息赋值
     *
     * @param iterator 创建的信息
     */
    default <Entity> void batchSaveDefaultAssignment(Iterator<Entity> iterator) {
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            this.saveDefaultAssignment(entity);
        }
    }


    /**
     * 默认创建信息赋值
     *
     * @param entity 创建的信息
     */
    default <Entity> void updateDefaultAssignment(Entity entity) {
        // 设置默认属性值
//        this.setPropertiesValue(entity, "updateTime", DateUtil.date());
    }

    /**
     * 是否包含该属性
     *
     * @param entity     实体对象
     * @param properties 属性
     * @return java.lang.Boolean
     */
    default <Entity> Boolean hasProperties(Entity entity, String properties) {
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (declaredField.getName().equals(properties)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含ID属性
     *
     * @param entity            实体对象
     * @param idAnnotationClass 属性
     * @return java.lang.String
     */
    default <Entity, T extends Annotation> String hasTableIdProperties(Entity entity, Class<T> idAnnotationClass) {
        // 默认ID字段
        String defaultIdField = "id";
        // 是否存在默认的ID
        boolean hasDefaultIdFiled = false;
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            Optional<T> optionalAnnotation = Optional.ofNullable(declaredField.getAnnotation(idAnnotationClass));
            // 设置有ID指端注解，且存在，则返回当前的自端名称
            if (optionalAnnotation.isPresent()) {
                return declaredField.getName();
            }

            // 预留注解方式不存在时，判断是否存在默认ID
            if (declaredField.getName().equals(defaultIdField)) {
                hasDefaultIdFiled = true;
            }
        }

        // 无注解，但又默认ID
        if (hasDefaultIdFiled) {
            return defaultIdField;
        }

        return null;
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
        if (this.hasProperties(entity, properties)) {
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
     * @param entity        目标对象
     * @param propertiesMap 目标属性集合
     */
    default <Entity> void setPropertiesValue(Entity entity, Map<String, Object> propertiesMap) {
        for (Map.Entry<String, Object> next : propertiesMap.entrySet()) {
            this.setPropertiesValue(entity, next.getKey(), next.getValue());
        }
    }

    /**
     * 设置属性值
     *
     * @param entity     目标对象
     * @param properties 目标属性
     * @param value      目标属性值
     */
    default <Entity> void setPropertiesValue(Entity entity, String properties, Object value) {
        // 如果存在属性
        if (this.hasProperties(entity, properties)) {
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
