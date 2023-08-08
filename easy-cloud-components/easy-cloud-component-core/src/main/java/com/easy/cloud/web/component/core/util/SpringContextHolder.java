package com.easy.cloud.web.component.core.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * 上下文
 *
 * @author GR
 * @date 2020-11-3 17:39
 */
public class SpringContextHolder implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  /**
   * 设置spring上下文  *  * @param applicationContext spring上下文  * @throws BeansException
   *
   * @param applicationContext 上下文
   */
  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
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
   * @param clazz class
   * @return T
   */
  public static <T> T getBean(Class<? extends T> clazz, Class<T> supperClazz) {
    return getApplicationContext().getBean(StrUtil.lowerFirst(clazz.getSimpleName()), supperClazz);
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

  /**
   * 发布事件
   *
   * @param event
   */
  public static void publishEvent(ApplicationEvent event) {
    if (applicationContext == null) {
      return;
    }
    applicationContext.publishEvent(event);
  }
}
