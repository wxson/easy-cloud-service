package com.easy.cloud.web.module.log.aspect;

import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.module.log.annotation.OperationRecord;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 日志处理类
 *
 * @author GR
 * @date 2021-8-4 14:03
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class OperationRecordAspect {

    @SneakyThrows
    @Around("@annotation(operationRecord)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, OperationRecord operationRecord) {
        com.easy.cloud.web.module.log.domain.OperationRecord record = com.easy.cloud.web.module.log.domain.OperationRecord.build();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        record.setClassName(className).setMethodName(methodName);
        log.debug("[类名]:{},[方法]:{}", className, methodName);
        Object[] objs = proceedingJoinPoint.getArgs();
        record.setName(operationRecord.value()).setParams(JSONUtil.toJsonStr(objs));
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        Long endTime = System.currentTimeMillis();
        record.setElapsedTime(endTime - startTime);
        return obj;
    }
}
