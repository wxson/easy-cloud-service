package com.easy.cloud.web.component.security.feign;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.security.constants.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Collection;


/**
 * @author GR
 * @date 2021-3-26 17:12
 */
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {
    /**
     * Create a template with the header of provided name and extracted extract
     * 1. 如果使用 非web 请求，header 区别
     * 2. 根据authentication 还原请求token
     *
     * @param requestTemplate 请求示例
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Collection<String> fromHeader = requestTemplate.headers().get(SecurityConstants.ORIGIN);
        if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityConstants.INNER_ORIGIN)) {
            return;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            requestTemplate.header("Authorization", String.format("%s %s", "Bearer", details.getTokenValue()));
        }
    }
}
