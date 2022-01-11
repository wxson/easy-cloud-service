package com.easy.cloud.web.component.core.exception;

import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.easy.cloud.web.component.core.response.HttpResult;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理类
 *
 * @author GR
 * @date 2021-3-26 14:11
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
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
     * 方法参数异常校验
     *
     * @param methodArgumentNotValidException 异常
     * @return com.easy.cloud.web.component.core.response.HttpResult<T>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> HttpResult<T> bindException(MethodArgumentNotValidException methodArgumentNotValidException) {
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        return HttpResult.fail(HttpResultEnum.FAIL.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /**
     * 异常处理类
     *
     * @param exception 业务异常
     * @return com.easy.cloud.web.component.core.response.HttpResult<T>
     */
    @ExceptionHandler(Exception.class)
    public <T> HttpResult<T> exceptionHandler(Exception exception) {
        return HttpResult.fail(HttpResultEnum.FAIL.getCode(), exception.toString());
    }
}
