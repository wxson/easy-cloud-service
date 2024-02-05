package com.easy.cloud.web.service.order.biz.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单未支付过期任务
 *
 * @author GR
 * @date 2024/2/5 10:03
 */
@Slf4j
@Component
public class OrderUnpaidExpiredJob {

    /**
     * 订单未支付过期任务回调
     */
    @XxlJob("orderUnpaidExpiredJob")
    public void orderUnpaidExpiredJob() {

    }
}
