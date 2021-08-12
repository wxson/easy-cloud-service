package com.easy.cloud.web.component.core.tenant;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 多租户配置
 *
 * @author GR
 * @date 2021-4-7 10:19
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "jiayou.tenant")
public class TenantConfigProperties {

    /**
     * 维护租户列名称
     */
    private String column = "tenant_id";

    /**
     * 多租户的数据表集合
     */
    private List<String> tables = Lists.newArrayList();

}
