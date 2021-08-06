package com.easy.cloud.web.component.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 白名单配置
 *
 * @author GR
 * @date 2021-3-26 14:30
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.ignore.urls")
public class GatewayIgnoreUrlsConfiguration {
    /**
     * 不需要验证的url
     */
    private List<String> ignoreUrlList;
}
