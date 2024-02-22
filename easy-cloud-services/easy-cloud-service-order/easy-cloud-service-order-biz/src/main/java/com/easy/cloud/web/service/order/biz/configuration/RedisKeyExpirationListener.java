package com.easy.cloud.web.service.order.biz.configuration;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.service.order.biz.constants.OrderConstants;
import com.easy.cloud.web.service.order.biz.job.OrderUnpaidExpiredJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * 监听所有db的过期事件__keyevent@*__:expired"
 * <p>注意：记得开启Redis Key 过期通知：</p>
 * <p>控制台：config set notify-keyspace-events Ex</p>
 * <p>配置文件：notify-keyspace-events Ex</p>
 *
 * @author GR
 * @date 2024-2-22 23:44
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OrderUnpaidExpiredJob orderUnpaidExpiredJob;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        // 尝试获取key标识
        if (StrUtil.isNotBlank(expiredKey) && expiredKey.startsWith(OrderConstants.ORDER_PAY_EXPIRED_KEY)) {
            String orderNo = expiredKey.substring(expiredKey.lastIndexOf(":") + 1);
            orderUnpaidExpiredJob.orderUnpaidExpiredJob(orderNo);
        }
    }
}
