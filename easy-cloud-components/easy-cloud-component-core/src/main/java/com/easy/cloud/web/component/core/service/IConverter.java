package com.easy.cloud.web.component.core.service;

import com.easy.cloud.web.component.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 数据转换方法
 *
 * @author GR
 * @date 2020-11-4 15:53
 */
public interface IConverter<Entity> extends IConvertProxy {

    /**
     * 当前对象转为泛型对象
     *
     * @param ignoreProperties 过滤的熟悉
     * @return TO
     */
    default Entity convert(String... ignoreProperties) {
        try {
            // 获取通用类型信息
            Type[] genericInterfaces = this.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    // 确保只有一个泛型
                    if (actualTypeArguments.length == 1) {
                        Class<Entity> actualTypeArgument = (Class<Entity>) actualTypeArguments[0];
                        Constructor<Entity> declaredConstructor = actualTypeArgument.getDeclaredConstructor();
                        declaredConstructor.setAccessible(true);
                        Entity instance = declaredConstructor.newInstance();
                        BeanUtils.copyProperties(this, instance, ignoreProperties);
                        this.initDefaultValue(instance);
                        return instance;
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BusinessException("IConverter.doConvert() 数据转换异常" + e.getMessage());
        }

        throw new BusinessException("IConverter.doConvert() 数据转换异常，未指定泛型");
    }

}
