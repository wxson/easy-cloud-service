package com.easy.cloud.web.aspect;

import cn.hutool.core.lang.Pair;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.configuration.StandAloneProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * OpenFeign AOP
 *
 * @author GR
 * @date 2024/2/21 18:36
 */
@Slf4j
@Aspect
@Component
public class StandAloneFeignAspect {

    @Autowired
    private StandAloneProperties standAloneProperties;

    @Around("execution(* com.easy.cloud.web..api.feign..*(..))")
    public Object point(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取目标对象
        Signature signature = proceedingJoinPoint.getSignature();
        // 获取目标方法
        Method method = ((MethodSignature) signature).getMethod();
        // match url
        Pair<String, Method> matchPair = null;
        // GET REQUEST
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (Objects.nonNull(getMapping)) {
            String matchUrl = getMapping.value()[0];
            matchPair = standAloneProperties.match(RequestMethod.GET, matchUrl);
        }

        // POST REQUEST
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (Objects.nonNull(postMapping)) {
            String matchUrl = postMapping.value()[0];
            matchPair = standAloneProperties.match(RequestMethod.POST, matchUrl);
        }

        // 未匹配到，则执行默认方法
        if (Objects.isNull(matchPair)) {
            Object[] declaredAnnotations = method.getDeclaredAnnotations();
            log.error("easy-cloud-stand-alone around exception：Methods other than get and post are not supported：{}", declaredAnnotations);
            return proceedingJoinPoint.proceed();
        }

        // 获取Spring Bean
        Object bean = SpringContextHolder.getApplicationContext().getBean(matchPair.getKey());
        // 调用方法
        return matchPair.getValue().invoke(bean, proceedingJoinPoint.getArgs());
    }
}
