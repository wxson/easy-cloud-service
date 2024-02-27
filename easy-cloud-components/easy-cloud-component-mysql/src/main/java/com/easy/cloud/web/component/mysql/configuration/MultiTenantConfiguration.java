package com.easy.cloud.web.component.mysql.configuration;

import cn.hutool.core.util.ClassUtil;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.utils.TenantTableHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

/**
 * @author GR
 * @date 2024/2/22 10:47
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "easy-cloud.multi-tenant")
public class MultiTenantConfiguration {

    /**
     * 扫描当前支持租户的数据库表
     */
    @PostConstruct
    public void scanTenantTable() {
        // 扫描多租户实体对象
        Set<Class<?>> tenantEntities = ClassUtil.scanPackageByAnnotation("com.easy.cloud.web", EnableTenant.class);
        for (Class<?> tenantEntity : tenantEntities) {
            // 获取租户注解
            EnableTenant enableTenantAnnotation = tenantEntity.getAnnotation(EnableTenant.class);
            if (Objects.isNull(enableTenantAnnotation)) {
                continue;
            }

            // 获取数据库表注解，读取表名称
            Table tableAnnotation = tenantEntity.getAnnotation(Table.class);
            if (Objects.nonNull(tableAnnotation)) {
                TenantTableHolder.addTenantTable(tableAnnotation.name());
            }
        }
    }
}
