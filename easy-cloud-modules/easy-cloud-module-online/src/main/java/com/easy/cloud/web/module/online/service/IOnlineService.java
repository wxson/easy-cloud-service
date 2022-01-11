package com.easy.cloud.web.module.online.service;

import java.util.Optional;

/**
 * @author GR
 * @date 2021-12-21 9:42
 */
public interface IOnlineService {

    /**
     * 新增在线人数
     *
     * @param number 新增人数
     * @return java.lang.Integer
     */
    Integer addOnlineNumber(Integer number);

    /**
     * 移除在线人数
     *
     * @param number 新增人数
     * @return java.lang.Integer
     */
    Integer removeOnlineNumber(Integer number);

    /**
     * 获取在线人数
     *
     * @return java.lang.Integer
     */
    Integer getOnlineNumber();

    /**
     * 添加在线人数
     *
     * @param keyCharSequence key
     * @param objects         参数
     * @return java.lang.Integer
     */
    Integer addOnLineNumber(Integer number, CharSequence keyCharSequence, Object... objects);

    /**
     * 添加在线人数
     *
     * @param number          数量
     * @param keyCharSequence key
     * @param objects         参数
     * @return java.lang.Integer
     */
    Integer removeOnLineNumber(Integer number, CharSequence keyCharSequence, Object... objects);

    /**
     * 获取在线人数
     *
     * @param keyCharSequence 新增人数
     * @param objects         参数
     * @return com.easy.cloud.web.module.online.domain.vo.OnlineVO
     */
    Integer getOnlineNumber(CharSequence keyCharSequence, Object... objects);

    /**
     * 保存redis对象
     *
     * @param data            存储对象
     * @param keyCharSequence key
     * @param objects         构建key参数
     */
    void saveRedisStore(Object data, CharSequence keyCharSequence, Object... objects);

    /**
     * @param tClass          类对象
     * @param keyCharSequence key
     * @param objects         构建key参数
     * @return T
     */
    <T> Optional<T> readRedisStore(Class<T> tClass, CharSequence keyCharSequence, Object... objects);
}
