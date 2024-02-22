package com.easy.cloud.web.aspect;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.annotation.StandAlone;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Arrays;
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
@AllArgsConstructor
public class FeignAspect {

    private EntityManager entityManager;

    @SneakyThrows
    @Around("@annotation(standAlone)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, StandAlone standAlone) {
        log.info("---------------FeignAspect-----------------------{}", proceedingJoinPoint.getArgs());
        Object bean = SpringContextHolder.getApplicationContext().getBean(standAlone.beanName());
        if (Objects.isNull(bean)) {
            throw new BusinessException(StrUtil.format("Stand Alone Run Model Exception，Not Find Target Bean：{}.{}", standAlone.packageName(), standAlone.beanName()));
        }
        // 获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 反射调用，跳过Http调用
        return bean.getClass()
                .getMethod(standAlone.invokeMethod(), Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new))
                .invoke(bean, proceedingJoinPoint.getArgs());
    }
}
