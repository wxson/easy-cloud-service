package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.order.biz.domain.db.OrderDO;
import com.easy.cloud.web.service.order.biz.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.biz.domain.vo.OrderVO;

/**
 * Order 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-11-12
 */
public interface IOrderService extends IRepositoryService<OrderDO> {

    /**
     * 创建订单
     *
     * @param orderDTO 订单数据
     * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
     */
    OrderVO createOrder(OrderDTO orderDTO);

    /**
     * 支付成功回调
     *
     * @param orderNo 订单编号
     * @return java.lang.Boolean
     */
    Boolean paySuccessHandler(String orderNo);
}