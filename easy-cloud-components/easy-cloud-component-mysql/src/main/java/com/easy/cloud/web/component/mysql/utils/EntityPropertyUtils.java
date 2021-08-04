package com.easy.cloud.web.component.mysql.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 获取实体的属性工具
 *
 * @author GR
 * @date 2021-4-20 9:42
 */
@UtilityClass
public class EntityPropertyUtils {


    /**
     * 获取对象所有的属性值
     *
     * @param entity 实体对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public <Entity> Map<String, Object> getAllPropertyValue(Entity entity) {
        HashMap<String, Object> propertyValueMap = CollUtil.newHashMap();
        if (Objects.isNull(entity)) {
            return propertyValueMap;
        }

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 获取字段名称
            String fieldName = declaredField.getName();
            // 构建BeanWrapper
            BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
            // 根据字段名称获取当前字段的值
            Optional<Object> optional = Optional.ofNullable(beanWrapper.getPropertyValue(fieldName));
            // 当前字段存在值，则加入
            if (optional.isPresent() && StrUtil.isNotBlank(optional.get().toString())) {
                propertyValueMap.put(fieldName, optional.get());
            }
        }
        return propertyValueMap;
    }

    /**
     * 获取对象TableField注解的所有的属性值
     *
     * @param entity 实体对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public <Entity> Map<String, Object> getAllTableFieldPropertyValue(Entity entity) {
        HashMap<String, Object> propertyValueMap = CollUtil.newHashMap();
        if (Objects.isNull(entity)) {
            return propertyValueMap;
        }

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 获取字段名称
            String fieldName = declaredField.getName();
            // 构建BeanWrapper
            BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
            // 根据字段名称获取当前字段的值
            Optional<Object> optional = Optional.ofNullable(beanWrapper.getPropertyValue(fieldName));
            // 获取注解字段
            Optional<TableField> optionalTableField = Optional.ofNullable(declaredField.getAnnotation(TableField.class));
            // 若存在标识字段
            if (optionalTableField.isPresent() && optional.isPresent() && StrUtil.isNotBlank(optional.get().toString())) {
                propertyValueMap.put(optionalTableField.get().value(), optional.get());
            }
        }
        return propertyValueMap;
    }

    /**
     * 获取对象逻辑删除地段
     *
     * @param cla 实体对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public <Entity> Map<String, Object> getTableLogicPropertyValue(Class<?> cla) {
        HashMap<String, Object> propertyValueMap = CollUtil.newHashMap();
        Field[] declaredFields = cla.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 获取注解字段
            Optional<TableLogic> optionalTableLogic = Optional.ofNullable(declaredField.getAnnotation(TableLogic.class));
            // 若存在标识字段
            optionalTableLogic.ifPresent(tableLogic -> propertyValueMap.put(declaredField.getName(), tableLogic.value()));
        }

        return propertyValueMap;
    }

    /**
     * 是否包含该属性
     *
     * @param entity     实体对象
     * @param properties 属性
     * @return java.lang.Boolean
     */
    public <Entity> Boolean hasProperties(Entity entity, String properties) {
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
    public <Entity, ReturnType> ReturnType getPropertiesValue(Entity entity, String properties) {
        // 是否存在属性
        if (EntityPropertyUtils.hasProperties(entity, properties)) {
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
     * 获取属性值
     *
     * @param entity 目标对象
     * @return java.lang.Object
     */
    public <Entity, ReturnType> ReturnType getTableIdPropertiesValue(Entity entity) {
        // 不存在则尝试获取@TableId字段
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 获取注解字段
            Optional<TableId> optionalTableId = Optional.ofNullable(declaredField.getAnnotation(TableId.class));
            // 若不存在TableId标识字段，则返回
            if (!optionalTableId.isPresent()) {
                continue;
            }
            // 获取字段名称
            String fieldName = declaredField.getName();
            // 构建BeanWrapper
            BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
            // 根据字段名称获取当前字段的值
            Optional<Object> optional = Optional.ofNullable(beanWrapper.getPropertyValue(fieldName));
            if (optional.isPresent() && StrUtil.isNotBlank(optional.get().toString())) {
                return (ReturnType) optional.get();
            }
        }
        return null;
    }
}
