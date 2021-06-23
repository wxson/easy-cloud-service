package com.easy.cloud.web.component.core.util;

import com.easy.cloud.web.component.core.exception.BusinessException;

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
}
