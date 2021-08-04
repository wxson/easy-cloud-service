package com.easy.cloud.web.component.mysql.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Optional;


/**
 * 树形节点Sql工具
 *
 * @author GR
 * @date 2021-5-19 18:04
 */
@UtilityClass
public class TreeNodeSqlUtils {

    /**
     * result使用sql替换
     */
    private final String DEFAULT_TREE_SQL_TEMPLATE = "SELECT CONCAT( {} ) str FROM {} t_0 ";
    private final String SELECT_SQL_TEMPLATE = "( SELECT * FROM `{}` WHERE {} )";

    private final String LEFT_JOIN_SQL_TEMPLATE = "LEFT JOIN `{}` {} ON {}.parent_id = {}.{} ";
    private final String IF_NULL_TEMPLATE = "IFNULL( {}.{}, 0 ) ";

    /**
     * 构建树形节点层数Sql,默认5层
     *
     * @param selectSql   目标类
     * @param targetClass 目标类
     * @return java.lang.String
     */
    public <QueryCondition> String buildSql(String selectSql, Class<?> targetClass) {
        return buildSql(5, selectSql, targetClass);
    }

    /**
     * 构建树形节点层数Sql
     *
     * @param treeLayer   树形节点层数
     * @param selectSql   目标类
     * @param targetClass 目标类
     * @return java.lang.String
     */
    public <QueryCondition> String buildSql(Integer treeLayer, String selectSql, Class<?> targetClass) {
        StringBuilder builder = new StringBuilder();
        StringBuilder leftJoinBuilder = new StringBuilder();
        StringBuilder concatBuilder = new StringBuilder();
        String tableName = getTableName(targetClass);
        String tableId = getTableId(targetClass);
        if (StrUtil.isBlank(tableName) || StrUtil.isBlank(tableId)) {
            return null;
        }

        int index = 0;
        concatBuilder.append(StrUtil.format(IF_NULL_TEMPLATE, "t_" + index, tableId));
        while (index < treeLayer) {
            String currentTable = "t_" + index;
            index++;
            String nextTable = "t_" + index;
            concatBuilder.append(",").append("\":\",");
            // left join
            leftJoinBuilder.append(StrUtil.format(LEFT_JOIN_SQL_TEMPLATE, tableName, nextTable, currentTable, nextTable, tableId));
            concatBuilder.append(StrUtil.format(IF_NULL_TEMPLATE, nextTable, tableId));
        }

        String format = StrUtil.format(SELECT_SQL_TEMPLATE, tableName, selectSql);
        builder.append(StrUtil.format(DEFAULT_TREE_SQL_TEMPLATE, concatBuilder.toString(), format))
                .append(leftJoinBuilder.toString());
        return builder.toString();
    }

    /**
     * 获取表名称
     *
     * @param targetClass 目标类
     * @return java.lang.String
     */
    public String getTableName(Class<?> targetClass) {
        Optional<TableName> optionalTableName = Optional.ofNullable(targetClass.getDeclaredAnnotation(TableName.class));
        return optionalTableName.map(TableName::value).orElse(null);
    }

    /**
     * 获取表ID
     *
     * @param targetClass 目标类
     * @return java.lang.String
     */
    public String getTableId(Class<?> targetClass) {
        // 不存在则尝试获取@TableId字段
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 获取注解字段
            Optional<TableId> optionalTableId = Optional.ofNullable(declaredField.getAnnotation(TableId.class));
            // 若不存在TableId标识字段，则返回
            if (optionalTableId.isPresent()) {
                String value = optionalTableId.get().value();
                if (StrUtil.isNotBlank(value)) {
                    return value;
                }
                return declaredField.getName();
            }
        }
        return null;
    }
}
