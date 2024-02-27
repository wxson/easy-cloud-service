package com.easy.cloud.web.component.mysql.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * SQL 条件工具
 *
 * @author GR
 * @date 2024/2/27 10:38
 */
public class SqlConditionWrapper {

    private ITableFieldConditionDecision conditionDecision;

    public SqlConditionWrapper(ITableFieldConditionDecision conditionDecision) {
        this.conditionDecision = conditionDecision;
    }

    /**
     * 为sql'语句添加指定where条件
     *
     * @param sqlStatement
     * @param fieldName
     * @param fieldValue
     */
    public void addStatementCondition(SQLStatement sqlStatement, String fieldName, String fieldValue) {
        if (sqlStatement instanceof SQLSelectStatement) {
            SQLSelectQueryBlock queryObject = (SQLSelectQueryBlock) ((SQLSelectStatement) sqlStatement).getSelect().getQuery();
            addSelectStatementCondition(queryObject, queryObject.getFrom(), fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLUpdateStatement) {
            SQLUpdateStatement updateStatement = (SQLUpdateStatement) sqlStatement;
            addUpdateStatementCondition(updateStatement, fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLInsertStatement) {
//            SQLInsertStatement insertStatement = (SQLInsertStatement) sqlStatement;
//            addInsertStatementCondition(insertStatement, fieldName, fieldValue);
            // TODO 插入语句暂不处理
            return;
        } else if (sqlStatement instanceof SQLDeleteStatement) {
            SQLDeleteStatement deleteStatement = (SQLDeleteStatement) sqlStatement;
            addDeleteStatementCondition(deleteStatement, fieldName, fieldValue);
            return;
        }
    }

    /**
     * 为insert语句添加where条件
     *
     * @param insertStatement
     * @param fieldName
     * @param fieldValue
     */
    private void addInsertStatementCondition(SQLInsertStatement insertStatement, String fieldName, String fieldValue) {
        if (insertStatement != null) {
            SQLInsertInto sqlInsertInto = insertStatement;
            SQLSelect sqlSelect = sqlInsertInto.getQuery();
            if (sqlSelect != null) {
                SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) sqlSelect.getQuery();
                addSelectStatementCondition(selectQueryBlock, selectQueryBlock.getFrom(), fieldName, fieldValue);
            } else {
                //判断是否需要加字段
                if (conditionDecision.adjudge(insertStatement.getTableName().getSimpleName(), fieldName)) {
                    //处理插入是没有包含字段得情况
                    if (!sqlInsertInto.getColumns().stream().anyMatch(e -> fieldName.equalsIgnoreCase(e.clone().toString()))) {
                        sqlInsertInto.getColumns().add(new SQLIdentifierExpr(fieldName));
                        sqlInsertInto.getValuesList().get(0).addValue(new SQLCharExpr(fieldValue));
                    }
//                    else if(sqlInsertInto.getColumns().indexOf(new SQLIdentifierExpr(fieldName))!=-1) {
//                        //这里是有值的情况 把租户值替换进去--这里的值是占位符有bug 改为前端转入
//                        int posIndex = sqlInsertInto.getColumns().indexOf(new SQLIdentifierExpr(fieldName));
//                        sqlInsertInto.getValuesList().get(0).getValues().set(posIndex, new SQLCharExpr(fieldValue));
//                        List<SQLInsertStatement.ValuesClause> valuesList = sqlInsertInto.getValuesList();
//                        List<SQLExpr> values = valuesList.get(0).getValues();
//                        values.set(posIndex, new SQLCharExpr(fieldValue));
//                    }
                }
            }
        }
    }


    /**
     * 为delete语句添加where条件
     *
     * @param deleteStatement
     * @param fieldName
     * @param fieldValue
     */
    private void addDeleteStatementCondition(SQLDeleteStatement deleteStatement, String fieldName, String fieldValue) {
        SQLExpr where = deleteStatement.getWhere();
        //添加子查询中的where条件
        addSQLExprCondition(where, fieldName, fieldValue);

        SQLExpr newCondition = newEqualityCondition(deleteStatement.getTableName().getSimpleName(),
                deleteStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
        deleteStatement.setWhere(newCondition);

    }

    /**
     * where中添加指定筛选条件
     *
     * @param where      源where条件
     * @param fieldName
     * @param fieldValue
     */
    private void addSQLExprCondition(SQLExpr where, String fieldName, String fieldValue) {
        if (where instanceof SQLInSubQueryExpr) {
            SQLInSubQueryExpr inWhere = (SQLInSubQueryExpr) where;
            SQLSelect subSelectObject = inWhere.getSubQuery();
            SQLSelectQueryBlock subQueryObject = (SQLSelectQueryBlock) subSelectObject.getQuery();
            addSelectStatementCondition(subQueryObject, subQueryObject.getFrom(), fieldName, fieldValue);
        } else if (where instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr opExpr = (SQLBinaryOpExpr) where;
            SQLExpr left = opExpr.getLeft();
            SQLExpr right = opExpr.getRight();
            addSQLExprCondition(left, fieldName, fieldValue);
            addSQLExprCondition(right, fieldName, fieldValue);
        } else if (where instanceof SQLQueryExpr) {
            SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) (((SQLQueryExpr) where).getSubQuery()).getQuery();
            addSelectStatementCondition(selectQueryBlock, selectQueryBlock.getFrom(), fieldName, fieldValue);
        }
    }

    /**
     * 为update语句添加where条件
     *
     * @param updateStatement
     * @param fieldName
     * @param fieldValue
     */
    private void addUpdateStatementCondition(SQLUpdateStatement updateStatement, String fieldName, String fieldValue) {
        SQLExpr where = updateStatement.getWhere();
        //添加子查询中的where条件
        addSQLExprCondition(where, fieldName, fieldValue);
        SQLExpr newCondition = newEqualityCondition(updateStatement.getTableName().getSimpleName(),
                updateStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
        updateStatement.setWhere(newCondition);
    }

    /**
     * 给一个查询对象添加一个where条件
     *
     * @param queryObject
     * @param fieldName
     * @param fieldValue
     */
    private void addSelectStatementCondition(SQLSelectQueryBlock queryObject, SQLTableSource from, String fieldName, String fieldValue) {
        if (StringUtils.isBlank(fieldName) || from == null || queryObject == null) {
            return;
        }

        SQLExpr originCondition = queryObject.getWhere();
        if (from instanceof SQLExprTableSource) {
            String tableName = ((SQLIdentifierExpr) ((SQLExprTableSource) from).getExpr()).getName();
            String alias = from.getAlias();
            SQLExpr newCondition = newInCondition(tableName, alias, fieldName, fieldValue, originCondition);
            queryObject.setWhere(newCondition);
        } else if (from instanceof SQLJoinTableSource) {
            SQLJoinTableSource joinObject = (SQLJoinTableSource) from;
            SQLTableSource left = joinObject.getLeft();
            SQLTableSource right = joinObject.getRight();
            addSelectStatementCondition(queryObject, left, fieldName, fieldValue);
            addSelectStatementCondition(queryObject, right, fieldName, fieldValue);
        } else if (from instanceof SQLSubqueryTableSource) {
            SQLSelect subSelectObject = ((SQLSubqueryTableSource) from).getSelect();
            SQLSelectQueryBlock subQueryObject = (SQLSelectQueryBlock) subSelectObject.getQuery();
            addSelectStatementCondition(subQueryObject, subQueryObject.getFrom(), fieldName, fieldValue);
        } else if (from instanceof SQLUnionQueryTableSource) {
            SQLUnionQueryTableSource union = (SQLUnionQueryTableSource) from;
            SQLUnionQuery sqlUnionQuery = union.getUnion();
            //这里判断查询类型
            addSelectStatementConditionUnion(queryObject, sqlUnionQuery, fieldName, fieldValue);
        } else {
            System.out.println("sql增强异常");
        }
    }

    /**
     * 拼接union查询的租户字段
     *
     * @param queryObject
     * @param sqlUnionQuery
     * @param fieldName
     * @param fieldValue
     */
    private void addSelectStatementConditionUnion(SQLSelectQueryBlock queryObject, SQLUnionQuery sqlUnionQuery, String fieldName, String fieldValue) {
        if (sqlUnionQuery.getLeft() instanceof SQLUnionQuery) {
            SQLUnionQuery temQuery = (SQLUnionQuery) sqlUnionQuery.getLeft();
            addSelectStatementConditionUnion(queryObject, temQuery, fieldName, fieldValue);
        }
        if (sqlUnionQuery.getLeft() instanceof SQLSelectQueryBlock) {
            SQLSelectQueryBlock left = (SQLSelectQueryBlock) sqlUnionQuery.getLeft();
            addSelectStatementCondition(left, left.getFrom(), fieldName, fieldValue);
        }
        if (sqlUnionQuery.getRight() instanceof SQLSelectQueryBlock) {
            SQLSelectQueryBlock right = (SQLSelectQueryBlock) sqlUnionQuery.getRight();
            addSelectStatementCondition(right, right.getFrom(), fieldName, fieldValue);
        }

    }

    /**
     * 根据原来的condition创建一个新的condition
     *
     * @param tableName       表名称
     * @param tableAlias      表别名
     * @param fieldName
     * @param fieldValue
     * @param originCondition
     * @return
     */
    private SQLExpr newEqualityCondition(String tableName, String tableAlias, String fieldName, String fieldValue, SQLExpr originCondition) {
        //如果不需要设置条件
        if (!conditionDecision.adjudge(tableName, fieldName)) {
            return originCondition;
        }
        SQLExpr condition = null;
        //如果条件字段不允许为空
        if (fieldValue == null) {
            condition = new SQLBinaryOpExpr(new SQLIdentifierExpr(fieldName), new SQLNullExpr(), SQLBinaryOperator.Is);
        } else {
            String filedName = StringUtils.isBlank(tableAlias) ? fieldName : tableAlias + "." + fieldName;
            condition = new SQLBinaryOpExpr(new SQLIdentifierExpr(filedName), new SQLCharExpr(fieldValue), SQLBinaryOperator.Equality);
        }
        return SQLUtils.buildCondition(SQLBinaryOperator.BooleanAnd, condition, false, originCondition);
    }

    private SQLExpr newInCondition(String tableName, String tableAlias, String fieldName, String fieldValue, SQLExpr originCondition) {
        //如果不需要设置条件
        if (!conditionDecision.adjudge(tableName, fieldName)) {
            return originCondition;
        }
        //如果条件字段允许为空
        if (fieldValue == null && !conditionDecision.isAllowNullValue()) {
            return originCondition;
        }
        String filedName = StringUtils.isBlank(tableAlias) ? fieldName : tableAlias + "." + fieldName;
        SQLExpr condition = null;
        if (fieldValue != null && fieldValue.contains(",")) {
            String[] split = fieldValue.split(",");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                if (i != split.length - 1) {
                    stringBuffer.append("\'" + split[i].trim() + "\',");
                } else {
                    stringBuffer.append("\'" + split[i].trim() + "\'");
                }
            }
            condition = new SQLIdentifierExpr(filedName + " in ( " + stringBuffer.toString() + ")");
        } else if (fieldValue == null) {
            condition = new SQLBinaryOpExpr(new SQLIdentifierExpr(fieldName), new SQLNullExpr(), SQLBinaryOperator.Is);
        } else {
            condition = new SQLBinaryOpExpr(new SQLIdentifierExpr(filedName), new SQLCharExpr(fieldValue), SQLBinaryOperator.Equality);
        }
        return SQLUtils.buildCondition(SQLBinaryOperator.BooleanAnd, condition, false, originCondition);
    }

    public interface ITableFieldConditionDecision {
        boolean adjudge(String tableName, String fieldName);

        boolean isAllowNullValue();
    }

    public static void main(String[] args) {
//        String sql = "select * from user s  ";
//        String sql = "select * from user s where s.name='333'";
//        String sql = "select * from (select * from tab t where id = 2 and name = 'wenshao') s where s.name='333'";
//        String sql="select u.*,g.name from user u join user_group g on u.groupId=g.groupId where u.name='123'";

//        String sql = "update user set name=? where id =(select id from user s)";
//        String sql = "delete from user where id = ( select id from user s )";

//        String sql = "INSERT INTO deleted_organization  ( id,origin_id,org_name,org_type,org_level,org_order,super_org_id,org_number,email,company_phone,is_structure,is_deleted,tag,data_source_flag,create_user_name,update_user_name,create_time,update_time,delete_time, tenant_id) VALUES( 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,8) ";
//        String sql = "SELECT count(0) FROM ((SELECT DISTINCT data_source_flag FROM t_account) UNION (SELECT DISTINCT data_source_flag FROM t_asset) UNION (SELECT DISTINCT data_source_flag FROM t_business_system) UNION (SELECT DISTINCT data_source_flag FROM t_component) UNION (SELECT DISTINCT data_source_flag FROM t_component_account) UNION (SELECT DISTINCT data_source_flag FROM t_employee) UNION (SELECT DISTINCT data_source_flag FROM t_intranet_ip_network_segment) UNION (SELECT DISTINCT data_source_flag FROM t_ip) UNION (SELECT DISTINCT data_source_flag FROM t_leak) UNION (SELECT DISTINCT data_source_flag FROM t_organization) UNION (SELECT DISTINCT data_source_flag FROM t_security_domain) UNION (SELECT DISTINCT data_source_flag FROM t_virus)) AS c LEFT JOIN d_enum e ON c.data_source_flag = e.code WHERE c.data_source_flag IS NOT NULL ";
//        String sql = "SELECT DISTINCT data_source_flag FROM t_account UNION SELECT DISTINCT data_source_flag FROM t_asset";
//        String sql = "update asset_ip_info set update_time=? where id=?";

        String sql = "select u.*,g.name from user u join (select * from user_group g  join user_role r on g.role_code=r.code  ) g on u.groupId=g.groupId where u.name='123'";
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.POSTGRESQL);
        SQLStatement sqlStatement = statementList.get(0);
        String dbType = sqlStatement.getDbType();
        //决策器定义
        SqlConditionWrapper helper = new SqlConditionWrapper(new ITableFieldConditionDecision() {
            @Override
            public boolean adjudge(String tableName, String fieldName) {
                return true;
            }

            @Override
            public boolean isAllowNullValue() {
                return true;
            }
        });
        //添加多租户条件，tenant_id是字段，yay是筛选值
        helper.addStatementCondition(sqlStatement, "tenant_id", "1");
        System.out.println("dbType：" + dbType);
        System.out.println("源sql：" + sql);
        System.out.println("修改后sql:" + SQLUtils.toSQLString(statementList, JdbcConstants.POSTGRESQL));
    }
}
