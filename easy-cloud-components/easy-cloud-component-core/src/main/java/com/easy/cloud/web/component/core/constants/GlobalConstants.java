package com.easy.cloud.web.component.core.constants;

/**
 * 全局公共常量
 *
 * @author GR
 * @date 2021-3-31 10:45
 */
public class GlobalConstants {

    /**
     * 默认属性结构顶端ID（即父级ID）
     */
    public static final String DEFAULT_TREE_PARENT_ID = "0";

    /**
     * 租户字段
     */
    public static final String TENANT_ID = "tenant_id";

    /**
     * 默认租户ID
     */
    public static final Integer DEFAULT_TENANT_ID = 1;

    /**
     * 常用整型
     */
    public static final int SIGN_ONE = -1;
    public static final int ZERO = 0;
    public static final long L_ZERO = 0;
    public static final int ONE = 1;
    public static final int EIGHT = 8;
    public static final int TEN = 10;
    public static final int TWELVE = 12;

    /**
     * 全局Redis前缀
     */
    public static final String REDIS_PREFIX_KEY = "easy_cloud:";
}
