package com.easy.cloud.web.component.mysql.utils;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.easy.cloud.web.component.core.tenant.TenantConfigProperties;
import com.easy.cloud.web.component.core.tenant.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 租户维护处理器
 *
 * @author GR
 * @date 2021-4-7 10:19
 */
@Slf4j
public class MysqlTenantHandler implements TenantHandler {

    @Autowired
    private TenantConfigProperties properties;

    @Override
    public Expression getTenantId() {
        Integer tenantId = TenantContextHolder.getTenantId();
        log.debug("当前租户为 >> {}", tenantId);

        if (ObjectUtil.isEmpty(tenantId)) {
            return new NullValue();
        }
        return new LongValue(tenantId);
    }

    /**
     * 获取租户字段名
     *
     * @return 租户字段名
     */
    @Override
    public String getTenantIdColumn() {
        return properties.getColumn();
    }

    /**
     * 根据表名判断是否进行过滤
     *
     * @param tableName 表名
     * @return 是否进行过滤
     */
    @Override
    public boolean doTableFilter(String tableName) {
        Integer tenantId = TenantContextHolder.getTenantId();
        // 租户中ID 为空，查询全部，不进行过滤
        if (ObjectUtil.isEmpty(tenantId)) {
            return Boolean.TRUE;
        }

        return !properties.getTables().contains(tableName);
    }
}
