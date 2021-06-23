package com.easy.cloud.web.component.core.exception;

import com.easy.cloud.web.component.core.response.HttpResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author GR
 * @date 2021-3-26 14:11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 通用业务异常处理类
     *
     * @param businessException 业务异常
     * @return com.easy.cloud.web.component.core.response.HttpResult<T>
     */
    @ExceptionHandler(BusinessException.class)
    public <T> HttpResult<T> businessExceptionHandler(BusinessException businessException) {
        return HttpResult.fail(businessException.getCode(), businessException.getMessage());
    }

    /**
     * 异常处理类
     *
     * @param businessException 业务异常
     * @return com.easy.cloud.web.component.core.response.HttpResult<T>
     */
    @ExceptionHandler(Exception.class)
    public <T> HttpResult<T> exceptionHandler(BusinessException businessException) {
        return HttpResult.fail(businessException.getCode(), businessException.getMessage());
    }
}
