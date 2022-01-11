package com.easy.cloud.web.component.security.aspect;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.component.security.constants.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务间接口不鉴权处理逻辑
 *
 * @author GR
 * @date 2021-3-26 16:48
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class SecurityInnerAspect {
    /**
     * http 请求
     */
    private final HttpServletRequest request;

    @SneakyThrows
    @Around("@annotation(inner)")
    public Object around(ProceedingJoinPoint point, Inner inner) {
        // 获取请求源
        String origin = request.getHeader(SecurityConstants.ORIGIN);
        // 非内部访问或非内部请求源，则不放行
        if (!inner.value() && !StrUtil.equals(SecurityConstants.INNER_ORIGIN, origin)) {
            log.error("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }
        return point.proceed();
    }

}
