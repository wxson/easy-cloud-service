package com.easy.cloud.web.component.core.aspect;

import com.easy.cloud.web.component.core.annotation.LimitFormRepeatSubmit;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 表单重复提交AOP
 *
 * @author GR
 * @date 2021-4-9 11:11
 */
@Aspect
public class LimitFormRepeatSubmitAspect {

    /**
     * 执行表单重复提交拦截
     *
     * @param proceedingJoinPoint   proceedingJoinPoint
     * @param limitFormRepeatSubmit 表单重复提交注解注解
     * @return java.lang.Object
     */
    @SneakyThrows
    @Around("@annotation(limitFormRepeatSubmit)")
    public Object limitFrequentRequestAspect(ProceedingJoinPoint proceedingJoinPoint, LimitFormRepeatSubmit limitFormRepeatSubmit) {
        return null;
    }
}
