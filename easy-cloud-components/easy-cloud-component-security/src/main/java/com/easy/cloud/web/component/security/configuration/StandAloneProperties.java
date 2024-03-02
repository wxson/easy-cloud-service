package com.easy.cloud.web.component.security.configuration;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ReUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
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
@ConditionalOnProperty(value = "easy-cloud.stand-alone", havingValue = "true")
public class StandAloneProperties implements InitializingBean {
    /**
     * 正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    @Autowired
    private WebApplicationContext applicationContext;

    private Map<String, Pair<String, Method>> requestMappingHandlerMappingUrls = new HashMap<>();

    private String generate(RequestMethod requestMethod, String requestUrl) {
        if (!requestUrl.startsWith("/")) {
            requestUrl = "/" + requestUrl;
        }
        return requestMethod.name() + " " + ReUtil.replaceAll(requestUrl, PATTERN, "*");
    }

    public void put(RequestMethod requestMethod, String requestUrl, Method method, String beanName) {
        requestUrl = this.generate(requestMethod, requestUrl);
        if (requestMappingHandlerMappingUrls.containsKey(requestUrl)) {
            log.error("Request Mapping Handler Url Repeat：{}", requestUrl);
        }
        requestMappingHandlerMappingUrls.put(requestUrl, Pair.of(beanName, method));
    }

    public Pair<String, Method> match(RequestMethod requestMethod, String requestUrl) {
        return Optional.ofNullable(requestMappingHandlerMappingUrls.get(this.generate(requestMethod, requestUrl)))
                .orElseThrow(() -> new BusinessException("Read Request Mapping Handler Url：" + requestUrl + " Fail!"));
    }

    @Override
    public void afterPropertiesSet() {
        // 获取所有api映射
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodsMap = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethodsMap.keySet().forEach(info -> {
            HandlerMethod handlerMethod = handlerMethodsMap.get(info);
            for (String pattern : info.getPatternsCondition().getPatterns()) {
                RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
                for (RequestMethod method : methodsCondition.getMethods()) {
                    this.put(method, pattern, handlerMethod.getMethod(), handlerMethod.getBean().toString());
                }
            }
        });
    }

}
