package com.easy.cloud.web.component.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author GR
 * @date 2021-4-14 14:03
 */
@Getter
@JsonSerialize(using = SecurityOauthExceptionSerializer.class)
public class SecurityOauthException extends OAuth2Exception {
    private int code;
    private String msg;
    private Object data;

    public SecurityOauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityOauthException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public SecurityOauthException(int code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.data = data;
    }
}
