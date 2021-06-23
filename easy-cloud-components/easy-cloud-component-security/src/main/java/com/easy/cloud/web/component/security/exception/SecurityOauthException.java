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
    private String describe;
    private Object data;

    public SecurityOauthException(String describe, Throwable t) {
        super(describe, t);
    }

    public SecurityOauthException(int code, String describe) {
        super(describe);
        this.code = code;
    }

    public SecurityOauthException(int code, String describe, Object data) {
        super(describe);
        this.code = code;
        this.data = data;
    }
}
