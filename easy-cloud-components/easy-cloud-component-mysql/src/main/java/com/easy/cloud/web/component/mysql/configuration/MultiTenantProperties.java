package com.easy.cloud.web.component.mysql.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 多租户模式
 *
 * @author GR
 * @date 2024/2/27 18:44
 */
@Getter
@Setter
@Component
@RefreshScope
@ConditionalOnProperty(value = "easy-cloud.multi-tenant")
public class MultiTenantProperties {
    /**
     * 是否启用：默认启用
     */
    private Boolean enabled = true;
}
