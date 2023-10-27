package com.easy.cloud.web.component.core.constants;

/**
 * @author GR
 * @date 2021-8-12 15:04
 */
public interface GlobalCacheConstants {
    /**
     * 缓存前缀
     */
    String BASE_PREFIX = "easy_cloud:cache:";

    /**
     * 客户认证缓存key
     */
    String CLIENT_DETAILS_KEY = BASE_PREFIX + "oauth:client_details_key:";

    /**
     * 客户认证缓存key
     */
    String GLOBAL_CONF_KEY = BASE_PREFIX + "global_conf_key:";
}
