package com.easy.cloud.web.service.order.biz.service.factory.order;

import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;

/**
 * @author GR
 * @date 2024/2/5 9:39
 */
public interface IOrderGenerateHandle {

    /**
     * 生成订单
     *
     * @param orderCreateDTO 订单创建信息
     * @return 返回对应的订单信息
     */
    OrderDO generateOrder(OrderCreateDTO orderCreateDTO);
}
