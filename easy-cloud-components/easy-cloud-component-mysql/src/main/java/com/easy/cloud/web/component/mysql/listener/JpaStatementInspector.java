package com.easy.cloud.web.component.mysql.listener;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.easy.cloud.web.component.core.tenant.TenantContextHolder;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.mysql.configuration.MultiTenantProperties;
import com.easy.cloud.web.component.mysql.constants.MysqlConstant;
import com.easy.cloud.web.component.mysql.utils.SqlConditionWrapper;
import com.easy.cloud.web.component.mysql.utils.TenantTableHolder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.List;
import java.util.Objects;

/**
 * JPA Statement Inspector
 * <p>1、为甚选择从SQL语句实现多租户逻辑：因为AOP、Filter等实现不适用于租户标记方法</p>
 *
 * @author GR
 * @date 2024/2/27 18:23
 */
public class JpaStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        try {
            MultiTenantProperties multiTenantProperties = SpringContextHolder.getBean(MultiTenantProperties.class);
            return Objects.isNull(multiTenantProperties) ? sql : reconstructionTenantSql(sql);
        } catch (Exception exception) {

        }
        return sql;
    }

    /**
     * 改造多租户sql语句逻辑
     *
     * @param sql 执行SQL
     * @return
     */
    private String reconstructionTenantSql(String sql) {
        // 租户为空，跳过
        if (StringUtils.isBlank(TenantContextHolder.getTenantId())) {
            return sql;
        }

        // 解析SQL
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        if (statementList == null || statementList.size() == 0) {
            return sql;
        }

        SQLStatement sqlStatement = statementList.get(0);
        // 重构SQL包装类
        SqlConditionWrapper sqlConditionWrapper = new SqlConditionWrapper(new SqlConditionWrapper.ITableFieldConditionDecision() {
            @Override
            public boolean adjudge(String tableName, String fieldName) {
                return TenantTableHolder.matchMethod(tableName);
            }

            @Override
            public boolean isAllowNullValue() {
                return false;
            }
        });

        sqlConditionWrapper.addStatementCondition(sqlStatement, MysqlConstant.TENANT_ID, TenantContextHolder.getTenantId());
        return SQLUtils.toSQLString(statementList, JdbcConstants.MYSQL);
    }
}
