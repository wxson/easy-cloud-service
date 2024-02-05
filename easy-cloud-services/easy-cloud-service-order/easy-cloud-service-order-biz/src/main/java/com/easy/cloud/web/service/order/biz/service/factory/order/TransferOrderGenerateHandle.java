package com.easy.cloud.web.service.order.biz.service.factory.order;

import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 转账订单生成器
 *
 * @author GR
 * @date 2024/2/5 9:41
 */
@Slf4j
@Service
public class TransferOrderGenerateHandle implements IOrderGenerateHandle {
    @Override
    public OrderDO generateOrder(OrderCreateDTO orderCreateDTO) {
        return null;
    }
}
