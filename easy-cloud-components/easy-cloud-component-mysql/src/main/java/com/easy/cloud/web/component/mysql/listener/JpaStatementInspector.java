package com.easy.cloud.web.component.mysql.listener;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.tenant.TenantContextHolder;
import com.easy.cloud.web.component.mysql.constants.MysqlConstant;
import com.easy.cloud.web.component.mysql.utils.LogicTableHolder;
import com.easy.cloud.web.component.mysql.utils.SqlConditionWrapper;
import com.easy.cloud.web.component.mysql.utils.TenantTableHolder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.List;

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
        // 解析SQL
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        if (statementList == null || statementList.size() == 0) {
            return sql;
        }

        // 租户增强
        this.tenantEnhancements(statementList);
        // 逻辑增强
        this.logicEnhancements(statementList);
        return SQLUtils.toSQLString(statementList, JdbcConstants.MYSQL);
    }

    /**
     * 租户增强
     *
     * @param statementList 执行SQL
     */
    private void tenantEnhancements(List<SQLStatement> statementList) {
        // 租户为空，跳过
        if (StringUtils.isBlank(TenantContextHolder.getTenantId())) {
            return;
        }

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

        sqlConditionWrapper.addStatementCondition(statementList.get(0), MysqlConstant.TENANT_ID, TenantContextHolder.getTenantId());
    }


    /**
     * 逻辑增强
     *
     * @param statementList 执行SQL
     */
    private String logicEnhancements(List<SQLStatement> statementList) {
        // 重构SQL包装类
        SqlConditionWrapper sqlConditionWrapper = new SqlConditionWrapper(new SqlConditionWrapper.ITableFieldConditionDecision() {
            @Override
            public boolean adjudge(String tableName, String fieldName) {
                return LogicTableHolder.match(tableName);
            }

            @Override
            public boolean isAllowNullValue() {
                return false;
            }
        });

        sqlConditionWrapper.addStatementCondition(statementList.get(0), MysqlConstant.LOGIC_DELETED, DeletedEnum.UN_DELETED.name());
        return SQLUtils.toSQLString(statementList, JdbcConstants.MYSQL);
    }
}
