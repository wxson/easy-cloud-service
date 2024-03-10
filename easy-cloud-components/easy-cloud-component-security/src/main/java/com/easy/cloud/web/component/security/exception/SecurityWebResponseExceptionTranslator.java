package com.easy.cloud.web.component.security.exception;

import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;

import java.util.Objects;

/**
 * @author GR
 * @date 2021-4-14 13:55
 */
public class SecurityWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception exception) throws Exception {
        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(exception);

        // 系统异常处理
        Exception ase = (BusinessException) throwableAnalyzer.getFirstThrowableOfType(BusinessException.class,
                causeChain);
        if (Objects.nonNull(ase)) {
            BusinessException businessException = (BusinessException) ase;
            return handleOauth2Exception(new SecurityOauthException(
                    Integer.parseInt(businessException.getCode().toString()), businessException.getMessage()));
        }

        // Token异常处理
        ase = (InvalidTokenException) throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class,
                causeChain);
        if (Objects.nonNull(ase)) {
            return handleOauth2Exception(new SecurityOauthException(HttpResultEnum.TOKEN_EXPIRED.getCode(),
                    HttpResultEnum.TOKEN_EXPIRED.getDesc()));
        }

        // 认证异常
        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                causeChain);
        if (Objects.nonNull(ase)) {
            return handleOauth2Exception(new SecurityOauthException(HttpStatus.UNAUTHORIZED.value(), ase.getMessage()));
        }

        // OAuth异常
        ase = (Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class,
                causeChain);
        if (Objects.nonNull(ase)) {
            return handleOauth2Exception(new SecurityOauthException(HttpStatus.UNAUTHORIZED.value(), ase.getMessage()));
        }

        // 其他异常
        return handleOauth2Exception(new SecurityOauthException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception));
    }

    private ResponseEntity<OAuth2Exception> handleOauth2Exception(OAuth2Exception exception) {
        int status = exception.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
        headers.set(HttpHeaders.PRAGMA, "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (exception instanceof InsufficientScopeException)) {
            headers.set(HttpHeaders.WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, exception.getSummary()));
        }

        return new ResponseEntity<>(exception, headers, HttpStatus.OK);
    }
}
