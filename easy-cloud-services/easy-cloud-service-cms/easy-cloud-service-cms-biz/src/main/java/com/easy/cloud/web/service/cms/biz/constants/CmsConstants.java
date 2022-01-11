package com.easy.cloud.web.service.cms.biz.constants;

/**
 * CMS 公共常量
 *
 * @author GR
 * @date 2021-11-29 10:32
 */
public class CmsConstants {
    /**
     * CMS redis 前缀
     */
    public static final String CMS_REDIS_PREFIX = "easy_cloud:cms:";
    /**
     * 免费领取商品前缀key
     * %s:%s  userId:goodsNo
     */
    public static final String FREE_RECEIVE_GOODS_KEY = CMS_REDIS_PREFIX + "receive:goods:{}:{}";
    /**
     * 玩家活跃前缀key
     * %s  userId
     */
    public static final String USER_ACTIVE_KEY = CMS_REDIS_PREFIX + "user:active:{}";
    /**
     * 用户当日签到
     * %s  userId:date
     */
    public static final String CURRENT_DAY_SIGN_IN = CMS_REDIS_PREFIX + "user:sign_in:{}:{}";
}
