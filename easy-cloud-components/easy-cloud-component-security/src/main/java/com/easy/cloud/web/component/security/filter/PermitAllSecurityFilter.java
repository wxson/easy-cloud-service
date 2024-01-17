package com.easy.cloud.web.component.security.filter;

import com.easy.cloud.web.component.security.configuration.PermitAllUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * OPEN API过滤器
 *
 * @author GR
 * @date 2024/1/16 11:06
 */
@Slf4j
@Component
public class PermitAllSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final String MATCH_STR = "*";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 请求地址
        String requestUrl = httpServletRequest.getRequestURI();
        // 是否存在匹配路径
        Optional<String> matchPermitAllUrlOptional = permitAllUrlProperties.getIgnoreUrls().stream()
                .map(url -> url.contains(MATCH_STR) ? url.substring(0, url.indexOf(MATCH_STR)) : url)
                .filter(requestUrl::startsWith)
                .findFirst();
        if (!matchPermitAllUrlOptional.isPresent()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 开放接口
        httpServletRequest = new HttpServletRequestWrapper(httpServletRequest) {

            private Set<String> headerNameSet;

            @Override
            public Enumeration<String> getHeaderNames() {
                if (headerNameSet == null) {
                    // first time this method is called, cache the wrapped request's header names:
                    headerNameSet = new HashSet<>();
                    Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
                    while (wrappedHeaderNames.hasMoreElements()) {
                        String headerName = wrappedHeaderNames.nextElement();
                        if (!AUTHORIZATION_HEADER.equalsIgnoreCase(headerName)) {
                            headerNameSet.add(headerName);
                        }
                    }
                }
                return Collections.enumeration(headerNameSet);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if (AUTHORIZATION_HEADER.equalsIgnoreCase(name)) {
                    return Collections.emptyEnumeration();
                }
                return super.getHeaders(name);
            }

            @Override
            public String getHeader(String name) {
                if (AUTHORIZATION_HEADER.equalsIgnoreCase(name)) {
                    return null;
                }
                return super.getHeader(name);
            }
        };

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
