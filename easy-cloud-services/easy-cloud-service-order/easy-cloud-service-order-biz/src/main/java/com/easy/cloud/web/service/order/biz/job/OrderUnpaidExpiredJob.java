package com.easy.cloud.web.service.order.biz.job;

import com.easy.cloud.web.component.pay.enums.PayStatusEnum;
import com.easy.cloud.web.service.order.api.dto.OrderRecordDTO;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import com.easy.cloud.web.service.order.biz.repository.OrderRepository;
import com.easy.cloud.web.service.order.biz.service.IOrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 订单未支付过期任务
 *
 * @author GR
 * @date 2024/2/5 10:03
 */
@Slf4j
@Component
public class OrderUnpaidExpiredJob {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IOrderRecordService orderRecordService;

    /**
     * 订单未支付过期任务回调
     *
     * @param orderNo 订单编号
     */
    public void orderUnpaidExpiredJob(String orderNo) {
        log.info("接收到订单支付超时：{}", orderNo);
        // 获取订单信息
        Optional<OrderDO> orderOptional = orderRepository.findByNo(orderNo);
        if (orderOptional.isPresent()) {
            OrderDO orderDO = orderOptional.get();
            // 若当前订单已支付成功，则改计时器失效
            if (PayStatusEnum.PAY_NO != orderDO.getPayStatus()) {
                log.info("订单：{} 支付倒计时结束，当前订单已完成支付，禁止修改状态", orderNo);
                return;
            }

            orderDO.setOrderStatus(OrderStatusEnum.INVALID);
            orderRepository.save(orderDO);
            log.info("接收到订单支付超时：{},修改订单为无效订单", orderNo);
            // 添加订单记录
            orderRecordService.save(OrderRecordDTO.builder()
                    .orderNo(orderNo)
                    .orderStatus(OrderStatusEnum.INVALID)
                    .remark("当前订单支付时间超时，属于无效订单")
                    .build());
        }
    }
}
