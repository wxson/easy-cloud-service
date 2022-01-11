package com.easy.cloud.web.component.extapi.aspect;

import com.easy.cloud.web.component.extapi.annotation.LimitFrequentRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 频繁请求AOP
 *
 * @author GR
 * @date 2021-4-9 11:12
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
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
