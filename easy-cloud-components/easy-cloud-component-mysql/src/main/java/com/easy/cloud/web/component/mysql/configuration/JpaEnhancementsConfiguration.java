package com.easy.cloud.web.component.mysql.configuration;

import cn.hutool.core.util.ClassUtil;
import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.component.mysql.utils.LogicTableHolder;
import com.easy.cloud.web.component.mysql.utils.TenantTableHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

/**
 * JPA 功能增强
 *
 * @author GR
 * @date 2024/2/22 10:47
 */
@Slf4j
@Configuration
public class JpaEnhancementsConfiguration {

    @PostConstruct
    public void scanTable() {
        // 扫描多租户实体对象
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper("com.easy.cloud.web", BaseEntity.class);
        for (Class<?> baseEntityChildren : classes) {
            // 获取数据库表注解，读取表名称
            Table tableAnnotation = baseEntityChildren.getAnnotation(Table.class);
            // 获取租户注解
            EnableTenant enableTenantAnnotation = baseEntityChildren.getAnnotation(EnableTenant.class);
            if (Objects.nonNull(enableTenantAnnotation) && Objects.nonNull(tableAnnotation)) {
                TenantTableHolder.addTenantTable(tableAnnotation.name());
            }

            // 获取逻辑删除注解
            EnableLogic enableLogicAnnotation = baseEntityChildren.getAnnotation(EnableLogic.class);
            if (Objects.nonNull(enableLogicAnnotation) && Objects.nonNull(tableAnnotation)) {
                LogicTableHolder.addLogicTable(tableAnnotation.name());
            }
        }
    }
}
