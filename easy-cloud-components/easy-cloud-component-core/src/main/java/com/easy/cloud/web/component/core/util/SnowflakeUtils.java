package com.easy.cloud.web.component.core.util;

import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 基于hu-tool IdUtil获取雪花ID
 * TODO 需解决WORK_ID与DATA_CENTER_ID的问题
 *
 * @author GR
 * @date 2021-2-18 14:28
 */
@Component
@AllArgsConstructor
public class SnowflakeUtils {

    /**
     * 机器ID 0~31
     */
    private static long WORK_ID;
    /**
     * 数据中心ID 0~31
     */
    private static long DATA_CENTER_ID;

    static {
        try {
            /*
             * 默认使用IP地址，进行hash计算，该方式只能适用于小部分分布式服务，会有一定概率的重复
             */
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            WORK_ID = Math.abs(hostAddress.hashCode() % 32);
            DATA_CENTER_ID = Math.abs((hostAddress.hashCode() + System.currentTimeMillis()) % 32);
        } catch (UnknownHostException ignored) {
            WORK_ID = 0;
            DATA_CENTER_ID = 0;
        }
    }

    /**
     * 获取雪花ID
     *
     * @return java.lang.String
     */
    public static String next() {
        return IdUtil.getSnowflake(WORK_ID, DATA_CENTER_ID).nextIdStr();
    }

    /**
     * 获取雪花ID
     *
     * @param workId       机器ID
     * @param dataCenterId 数据中心ID
     * @return java.lang.String
     */
    public static String next(long workId, long dataCenterId) {
        return IdUtil.getSnowflake(workId, dataCenterId).nextIdStr();
    }
}
