package com.easy.cloud.web.component.core.exception;

import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常类
 *
 * @author GR
 * @date 2020-9-11 10:56
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private int code = HttpResultEnum.FAIL.getCode();

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BusinessException(HttpResultEnum httpResultEnum) {
        super(httpResultEnum.getDesc());
        this.code = httpResultEnum.getCode();
    }

}
