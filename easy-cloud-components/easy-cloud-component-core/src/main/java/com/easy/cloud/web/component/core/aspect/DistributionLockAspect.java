package com.easy.cloud.web.component.core.aspect;

import com.easy.cloud.web.component.core.annotation.DistributionLock;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 分布式锁AOP
 *
 * @author GR
 * @date 2021-4-9 11:12
 */
@Aspect
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
