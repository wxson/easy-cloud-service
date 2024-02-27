package com.easy.cloud.web.component.mysql.constants;

/**
 * 租戶常量
 *
 * @author GR
 * @date 2023/11/10 11:20
 */
public class MysqlConstant {

    /**
     * 租户过滤器名字
     */
    public final static String TENANT_FILTER_NAME = "tenantFiler";
    /**
     * 租户过滤器字段
     */
    public final static String TENANT_ID = "tenant_test_id";
    /**
     * 租户过滤器参数类型
     */
    public final static String TENANT_PARAM_TYPE = "string";
    /**
     * 租户过滤器条件
     */
    public final static String TENANT_FILTER_CONDITION = TENANT_ID + " = :" + TENANT_ID;

    /**
     * 逻辑过滤器名字（这里指逻辑删除）
     */
    public final static String LOGIC_FILTER_NAME = "logicFiler";
    /**
     * 逻辑过滤器字段
     */
    public final static String LOGIC_DELETED = "deleted";
    /**
     * 逻辑过滤器参数类型
     */
    public final static String LOGIC_PARAM_TYPE = "string";
    /**
     * 逻辑过滤器条件
     */
    public final static String LOGIC_FILTER_CONDITION = LOGIC_DELETED + " = :" + LOGIC_DELETED;
}
