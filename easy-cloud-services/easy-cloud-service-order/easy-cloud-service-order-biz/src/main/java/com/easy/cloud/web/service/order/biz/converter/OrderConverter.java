package com.easy.cloud.web.service.order.biz.converter;

import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.order.api.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Order转换器
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
public class OrderConverter {

  /**
   * DTO转为DO
   *
   * @param order 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.db.OrderDO
   */
  public static OrderDO convertTo(OrderDTO order) {
    OrderDO orderDO = OrderDO.builder().build();
    BeanUtils.copyProperties(order, orderDO, true);
    return orderDO;
  }

  /**
   * DO转为VO
   *
   * @param order 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  public static OrderVO convertTo(OrderDO order) {
    OrderVO orderVO = OrderVO.builder().build();
    BeanUtils.copyProperties(order, orderVO, true);
    return orderVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param orders 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  public static List<OrderVO> convertTo(List<OrderDO> orders) {
    return orders.stream()
        .map(OrderConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  public static Page<OrderVO> convertTo(Page<OrderDO> page) {
    return page.map(OrderConverter::convertTo);
  }
}