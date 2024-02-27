package com.easy.cloud.web.component.mysql.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储滤掉不需要的租户方法
 * <p>一般租户的启用会在Class上全局注释，但因为业务逻辑导致某个方法中不能携带租户信息，故有此逻辑</p>
 * <p>比如：租户查询平台信息时，因为租户限制，导致无法查询的情况</p>
 *
 * @author GR
 * @date 2023/10/4
 */
@UtilityClass
public class TenantTableHolder {

    /**
     * 支持的租户表
     */
    private final List<String> TENANT_TABLES = new ArrayList<>();

    /**
     * 添加支持租户的表
     *
     * @param tenantTable 支持租户的表
     */
    public void addTenantTable(String tenantTable) {
        TENANT_TABLES.add(tenantTable);
    }

    /**
     * 匹配当前表是否支持租户逻辑
     *
     * @param tenantTable 支持租户的表
     * @return
     */
    public Boolean matchMethod(String tenantTable) {
        return TENANT_TABLES.contains(tenantTable);
    }
}
