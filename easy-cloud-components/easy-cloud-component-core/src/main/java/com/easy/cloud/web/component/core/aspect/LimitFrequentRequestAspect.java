package com.easy.cloud.web.component.core.aspect;

import com.easy.cloud.web.component.core.annotation.LimitFrequentRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 频繁请求AOP
 *
 * @author GR
 * @date 2021-4-9 11:12
 */
@Aspect
public class LimitFrequentRequestAspect {

    /**
     * 执行限流
     *
     * @param proceedingJoinPoint  proceedingJoinPoint
     * @param limitFrequentRequest 频繁访问注解
     * @return java.lang.Object
     */
    @SneakyThrows
    @Around("@annotation(limitFrequentRequest)")
    public Object limitFrequentRequestAspect(ProceedingJoinPoint proceedingJoinPoint, LimitFrequentRequest limitFrequentRequest) {
        return null;
    }
}
