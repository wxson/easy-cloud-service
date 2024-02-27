package com.easy.cloud.web.component.mysql.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑删除表数据
 *
 * @author GR
 * @date 2023/10/4
 */
@UtilityClass
public class LogicTableHolder {

    /**
     * 支持的逻辑删除表
     */
    private final List<String> LOGIC_TABLES = new ArrayList<>();

    /**
     * 添加支持逻辑删除的表
     *
     * @param logicTable 支持逻辑删除的表
     */
    public void addLogicTable(String logicTable) {
        LOGIC_TABLES.add(logicTable);
    }

    /**
     * 匹配当前表是否支持逻辑删除逻辑
     *
     * @param logicTable 持逻辑删除的表
     */
    public Boolean match(String logicTable) {
        return LOGIC_TABLES.contains(logicTable);
    }
}
