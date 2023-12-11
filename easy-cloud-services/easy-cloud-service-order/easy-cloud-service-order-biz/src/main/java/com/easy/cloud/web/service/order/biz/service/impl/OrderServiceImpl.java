package com.easy.cloud.web.service.order.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.order.api.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.api.enums.PayStatusEnum;
import com.easy.cloud.web.service.order.biz.converter.OrderConverter;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import com.easy.cloud.web.service.order.biz.repository.OrderRepository;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Order 业务逻辑
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public OrderVO save(OrderDTO orderDTO) {
    // 转换成DO对象
    OrderDO order = OrderConverter.convertTo(orderDTO);
    // TODO 校验逻辑

    // 存储
    orderRepository.save(order);
    // 转换对象
    return OrderConverter.convertTo(order);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public OrderVO update(OrderDTO orderDTO) {
    // 转换成DO对象
    OrderDO order = OrderConverter.convertTo(orderDTO);
    if (Objects.isNull(order.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    orderRepository.save(order);
    // 转换对象
    return OrderConverter.convertTo(order);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public Boolean removeById(Long orderId) {
    // TODO 业务逻辑校验

    // 删除
    orderRepository.deleteById(orderId);
    return true;
  }

  @Override
  public OrderVO detailById(Long orderId) {
    // TODO 业务逻辑校验

    // 删除
    OrderDO order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return OrderConverter.convertTo(order);
  }

  @Override
  public List<OrderVO> list() {
    // 获取列表数据
    List<OrderDO> orders = orderRepository.findAll();
    return OrderConverter.convertTo(orders);
  }

  @Override
  public Page<OrderVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return OrderConverter.convertTo(orderRepository.findAll(pageable));
  }


  @Override
  @Transactional(rollbackOn = Exception.class)
  public Boolean paySuccessHandler(String orderNo) {
    // 订单编号不能为空
    if (StrUtil.isBlank(orderNo)) {
      throw new BusinessException("当前订单编号不能为空");
    }

    // 获取订单详情
    OrderDO orderDO = orderRepository.findByNo(orderNo)
        .orElseThrow(() -> new BusinessException("当前订单信息不存在"));
    // 修改订单状态
    orderDO.setOrderStatus(OrderStatusEnum.PAID_OFF);
    // 支付状态
    orderDO.setPayStatus(PayStatusEnum.PAY_YES);
    // 创建支付时间
    orderDO.setCreatePayAt(DateUtil.now());
    orderRepository.save(orderDO);
    return true;
  }
}