package com.easy.cloud.web.component.core.util;

import cn.hutool.json.JSONUtil;

import java.util.Optional;

/**
 * 本地线程缓存工具
 *
 * @author GR
 * @date 2021-4-6 14:20
 */
@Deprecated
public class ThreadLocalUserUtils {
    /**
     * 获取ThreadLocal中的User用户字段信息
     *
     * @param key 用户信息字段
     * @return java.lang.String
     */
    public static String get(String key) {
        try {
            Optional<Object> optionalAuthenticationUserJson = ThreadLocalUtils.getThreadLocalKey(ThreadLocalUtils.THREAD_LOCAL_USER_MAP_KEY);
            if (optionalAuthenticationUserJson.isPresent()) {
                Object authenticationUserJson = optionalAuthenticationUserJson.get();
                return JSONUtil.parseObj(authenticationUserJson).getStr(key);
            }
        } catch (Exception ignored) {

        }

        return "";
    }
}
