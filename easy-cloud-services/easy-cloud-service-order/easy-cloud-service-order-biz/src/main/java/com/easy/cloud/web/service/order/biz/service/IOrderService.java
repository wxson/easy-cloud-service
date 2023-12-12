package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.service.order.api.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Order interface
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
public interface IOrderService {

  /**
   * 新增数据
   *
   * @param orderDTO 保存参数
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  OrderVO save(OrderDTO orderDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param orderDTO 保存参数
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  OrderVO update(OrderDTO orderDTO);

  /**
   * 根据ID删除数据
   *
   * @param orderId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(Long orderId);

  /**
   * 根据ID获取详情
   *
   * @param orderId 对象ID
   * @return java.lang.Boolean
   */
  OrderVO detailById(Long orderId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO> 返回列表数据
   */
  List<OrderVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO> 返回列表数据
   */
  Page<OrderVO> page(int page, int size);

  /**
   * 支付成功回调
   *
   * @param orderNo 订单编号
   * @return
   */
  Boolean paySuccessHandler(String orderNo);
}