package com.easy.cloud.web.component.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 本地线程缓存工具
 *
 * @author GR
 * @date 2021-4-6 14:20
 */
@Deprecated
public class ThreadLocalUtils {

    /**
     * TODO 注意：WebFlux模式下，思考ThreadLocal是否会被影响
     */
    private static final ThreadLocal<Map<String, Object>> MAP_THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();

    public static final String THREAD_LOCAL_USER_MAP_KEY = "auth_user_key";

    /**
     * 设置本地存储信息
     *
     * @param key    存储key
     * @param object 存储value
     */
    public static void setThreadLocalInfo(String key, Object object) {
        Optional<Map<String, Object>> optionalStringObjectMap = Optional.ofNullable(MAP_THREAD_LOCAL.get());
        if (optionalStringObjectMap.isEmpty()) {
            MAP_THREAD_LOCAL.set(new HashMap<>(8));
        }

        MAP_THREAD_LOCAL.get().put(key, object);
    }

    /**
     * 获取ThreadLocal 信息
     *
     * @param key 存储Key
     * @return java.util.Optional<java.lang.Object>
     */
    public static Optional<Object> getThreadLocalKey(String key) {
        Optional<Map<String, Object>> optionalStringObjectMap = Optional.ofNullable(MAP_THREAD_LOCAL.get());
        if (optionalStringObjectMap.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(optionalStringObjectMap.get().get(THREAD_LOCAL_USER_MAP_KEY));
    }

    public static void clear() {
        MAP_THREAD_LOCAL.remove();
    }

}
