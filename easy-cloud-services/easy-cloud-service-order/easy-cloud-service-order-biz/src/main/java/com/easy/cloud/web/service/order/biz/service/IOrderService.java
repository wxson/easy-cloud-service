package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.api.dto.OrderQueryDTO;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import org.springframework.data.domain.Page;

import java.util.List;

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
     * @param orderCreateDTO 订单创建数据
     * @return com.easy.cloud.web.service.order.api.vo.OrderVO
     */
    OrderVO createOrder(OrderCreateDTO orderCreateDTO);

    /**
     * 根据ID获取详情
     *
     * @param orderId 对象ID
     * @return java.lang.Boolean
     */
    OrderVO detailById(String orderId);

    /**
     * 根据订单号获取详情
     *
     * @param orderNo 订单号
     * @return java.lang.Boolean
     */
    OrderVO detailByNo(String orderNo);

    /**
     * 根据条件获取列表数据
     *
     * @return List<com.easy.cloud.web.service.order.api.vo.OrderVO> 返回列表数据
     */
    List<OrderVO> list();

    /**
     * 根据条件获取分页数据
     *
     * @param orderQueryDTO 订单查询参数
     * @return List<com.easy.cloud.web.service.order.api.vo.OrderVO> 返回列表数据
     */
    Page<OrderVO> page(OrderQueryDTO orderQueryDTO);

    /**
     * 支付成功回调
     *
     * @param orderNo 订单编号
     * @param tradeNo 支付平台交易单号
     * @return
     */
    Boolean paySuccessHandler(String orderNo, String tradeNo);

    /**
     * 订单确认
     *
     * @param orderId 订单ID
     * @return 返回确认后的订单
     */
    OrderVO confirmOrder(String orderId);

    /**
     * 已生成预付订单
     *
     * @param orderNo 订单号
     * @return 返回确认后的订单
     */
    OrderVO prePayOrder(String orderNo);
}