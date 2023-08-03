package com.easy.cloud.web.component.mongo.utils;

import com.easy.cloud.web.component.core.exception.BusinessException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import lombok.experimental.UtilityClass;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.web.method.HandlerMethod;


/**
 * 类工具类
 *
 * @author GR
 * @date 2020-11-4 15:53
 */
@UtilityClass
public class ClassUtil extends org.springframework.util.ClassUtils {

  private final ParameterNameDiscoverer PARAMETERNAMEDISCOVERER = new DefaultParameterNameDiscoverer();

  /**
   * 获取方法参数信息
   *
   * @param constructor    构造器
   * @param parameterIndex 参数序号
   * @return {MethodParameter}
   */
  public MethodParameter getMethodParameter(Constructor<?> constructor, int parameterIndex) {
    MethodParameter methodParameter = new SynthesizingMethodParameter(constructor, parameterIndex);
    methodParameter.initParameterNameDiscovery(PARAMETERNAMEDISCOVERER);
    return methodParameter;
  }

  /**
   * 获取方法参数信息
   *
   * @param method         方法
   * @param parameterIndex 参数序号
   * @return {MethodParameter}
   */
  public MethodParameter getMethodParameter(Method method, int parameterIndex) {
    MethodParameter methodParameter = new SynthesizingMethodParameter(method, parameterIndex);
    methodParameter.initParameterNameDiscovery(PARAMETERNAMEDISCOVERER);
    return methodParameter;
  }

  /**
   * 获取Annotation
   *
   * @param method         Method
   * @param annotationType 注解类
   * @param <A>            泛型标记
   * @return {Annotation}
   */
  public <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
    Class<?> targetClass = method.getDeclaringClass();
    // The method may be on an interface, but we need attributes from the target class.
    // If the target class is null, the method will be unchanged.
    Method specificMethod = ClassUtil.getMostSpecificMethod(method, targetClass);
    // If we are dealing with method with generic parameters, find the original method.
    specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
    // 先找方法，再找方法上的类
    A annotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod, annotationType);
    ;
    if (null != annotation) {
      return annotation;
    }
    // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
    return AnnotatedElementUtils
        .findMergedAnnotation(specificMethod.getDeclaringClass(), annotationType);
  }

  /**
   * 获取Annotation
   *
   * @param handlerMethod  HandlerMethod
   * @param annotationType 注解类
   * @param <A>            泛型标记
   * @return {Annotation}
   */
  public <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod,
      Class<A> annotationType) {
    // 先找方法，再找方法上的类
    A annotation = handlerMethod.getMethodAnnotation(annotationType);
    if (null != annotation) {
      return annotation;
    }
    // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
    Class<?> beanType = handlerMethod.getBeanType();
    return AnnotatedElementUtils.findMergedAnnotation(beanType, annotationType);
  }


  /**
   * <p>
   * 请仅在确定类存在的情况下调用该方法
   * </p>
   *
   * @param name 类名称
   * @return 返回转换后的 Class
   */
  public static Class<?> toClassConfident(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      throw new BusinessException("找不到指定的class！请仅在明确确定会有 class 的时候，调用该方法");
    }
  }
}
