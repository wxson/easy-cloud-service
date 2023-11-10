package com.easy.cloud.web.component.mysql.aspect;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author GR
 * @date 2023/11/10 17:02
 */
public interface TenantAdvice extends MethodBeforeAdvice, AfterReturningAdvice {

}
