package com.easy.cloud.web.component.core.service;

import com.easy.cloud.web.component.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
}
