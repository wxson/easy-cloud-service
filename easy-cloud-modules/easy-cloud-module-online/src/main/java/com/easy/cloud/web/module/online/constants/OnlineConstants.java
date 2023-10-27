package com.easy.cloud.web.module.online.constants;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;

/**
 * 在线常量
 *
 * @author GR
 * @date 2021-12-21 9:39
 */
public class OnlineConstants {

    /**
     * 在线前缀
     */
    public static final String ONLINE_PREFIX = GlobalCommonConstants.REDIS_PREFIX_KEY + "online:";

    /**
     * 在线数量key
     */
    public static final String ONLINE_USER_NUMBER_KEY = ONLINE_PREFIX + "user:number";
}
