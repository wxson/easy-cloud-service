package com.easy.cloud.web.component.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * 上下文
 *
 * @author GR
 * @date 2020-11-3 17:39
 */
public class SpringHolder implements ApplicationContextAware {
    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 设置spring上下文  *  * @param applicationContext spring上下文  * @throws BeansException
     *
     * @param applicationContext 上下文
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz class
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过class获取Bean.
     *
     * @param beanName Bean名称
     * @param clazz    class
     * @return T
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return getApplicationContext().getBean(beanName, clazz);
    }
}
