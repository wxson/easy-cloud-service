package com.easy.cloud.web.component.extapi.aspect;

import com.easy.cloud.web.component.extapi.annotation.DistributionLock;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 分布式锁AOP
 *
 * @author GR
 * @date 2021-4-9 11:12
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class DistributionLockAspect {
    /**
     * 分布式锁拦截,执行加锁
     *
     * @param proceedingJoinPoint   proceedingJoinPoint
     * @param redisDistributionLock 分布式锁注解
     * @return java.lang.Object
     */
    @SneakyThrows
    @Around("@annotation(redisDistributionLock)")
    public Object limitFrequentRequestAspect(ProceedingJoinPoint proceedingJoinPoint, DistributionLock redisDistributionLock) {
        return null;
    }
}
