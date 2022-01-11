package com.easy.cloud.web.component.security.mobile;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.security.constants.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 移动端过滤器
 * 参照UsernamePasswordAuthenticationFilter过滤器实现移动端快捷登录验证
 *
 * @author GR
 * @date 2021-11-3 10:16
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    @Getter
    @Setter
    private boolean postOnly = true;
    @Getter
    @Setter
    private AuthenticationEventPublisher authenticationEventPublisher;
    @Getter
    @Setter
    private AuthenticationEntryPoint authenticationEntryPoint;

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.MOBILE_TOKEN_URL_PREFIX, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //支持 post、get
        if (!(request.getMethod().equals(HttpMethod.POST.name()) || request.getMethod().equals(HttpMethod.GET.name()))) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        // 获取请求body
        BufferedReader requestReader = request.getReader();
        String requestBodyStr = requestReader.lines().collect(Collectors.joining());
        if (StrUtil.isBlank(requestBodyStr)) {
            requestBodyStr = SPRING_SECURITY_FORM_MOBILE_KEY;
        }
        // 创建移动授权凭证
        MobileAuthenticationToken mobileAuthenticationToken = new MobileAuthenticationToken(requestBodyStr.trim());
        // 设置全局认证信息
        SecurityContextHolder.getContext().setAuthentication(mobileAuthenticationToken);
        // Allow subclasses to set the "details" property
        setDetails(request, mobileAuthenticationToken);
        Authentication authResult = null;
        try {
            authResult = this.getAuthenticationManager().authenticate(mobileAuthenticationToken);

            logger.info("Authentication success: " + authResult);
            SecurityContextHolder.getContext().setAuthentication(authResult);

        } catch (Exception failed) {
            SecurityContextHolder.clearContext();
            logger.info("Authentication request failed: " + failed);

            authenticationEventPublisher.publishAuthenticationFailure(new BadCredentialsException(failed.getMessage(), failed),
                    new PreAuthenticatedAuthenticationToken("access-token", "N/A"));

            try {
                authenticationEntryPoint.commence(request, response,
                        new UsernameNotFoundException(failed.getMessage(), failed));
            } catch (Exception e) {
                logger.error("authenticationEntryPoint handle error:{}", failed);
            }
        }
        return authResult;
    }

    protected void setDetails(HttpServletRequest request,
                              MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
