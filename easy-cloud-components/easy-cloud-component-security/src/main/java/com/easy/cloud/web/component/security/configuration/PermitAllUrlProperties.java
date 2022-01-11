package com.easy.cloud.web.component.security.configuration;

import cn.hutool.core.util.ReUtil;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 资源服务器对外直接暴露URL,如果设置contex-path 要特殊处理
 *
 * @author GR
 * @date 2021-3-26 17:04
 */
@Slf4j
@Configuration
@RefreshScope
public class PermitAllUrlProperties implements InitializingBean {
    /**
     * 正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    @Autowired
    private WebApplicationContext applicationContext;

    @Getter
    @Setter
    private List<String> ignoreUrls = Lists.newArrayList();

    @Override
    public void afterPropertiesSet() {
        // 获取所有api映射
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodsMap = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethodsMap.keySet().forEach(info -> {
            HandlerMethod handlerMethod = handlerMethodsMap.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
            Optional.ofNullable(method)
                    .ifPresent(inner -> info.getPatternsCondition().getPatterns()
                            .forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

            // 获取类上边的注解, 替代path variable 为 *
            Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
            Optional.ofNullable(controller)
                    .ifPresent(inner -> info.getPatternsCondition().getPatterns()
                            .forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
        });

    }
}
