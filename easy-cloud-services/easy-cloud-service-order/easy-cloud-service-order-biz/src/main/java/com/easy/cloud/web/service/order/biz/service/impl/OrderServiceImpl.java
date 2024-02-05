package com.easy.cloud.web.service.order.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.pay.enums.PayStatusEnum;
import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.api.dto.OrderQueryDTO;
import com.easy.cloud.web.service.order.api.dto.OrderRecordDTO;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.converter.OrderConverter;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import com.easy.cloud.web.service.order.biz.repository.OrderRepository;
import com.easy.cloud.web.service.order.biz.service.IOrderRecordService;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import com.easy.cloud.web.service.order.biz.service.factory.OrderGenerateFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Order 业务逻辑
 * <p>1、创建订单</p>
 * <p>2、确认订单</p>
 * <p>3、订单支付</p>
 * <p>4、支付成功回调</p>
 * <p>5、商家确认发货，填写物流信息</p>
 * <p>6、客户确认收货，订单完成</p>
 * <p>6、客户确认收货</p>
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IOrderRecordService orderRecordService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderVO createOrder(OrderCreateDTO orderCreateDTO) {
        // 1、获取商品信息
        OrderDO orderDO = OrderGenerateFactory.generateOrder(orderCreateDTO.getOrderType(), orderCreateDTO);
        // 2、TODO 获取商品规格

        // 3、TODO 校验库存数量

        // 4、TODO 锁定库存

        // 5、TODO 获取收货地址

        // 6、TODO 获取优惠信息

        // 7、创建确认订单
        orderRepository.save(orderDO);
        // 存储订单记录
        orderRecordService.save(OrderRecordDTO.builder()
                .orderNo(orderDO.getNo())
                .orderStatus(orderDO.getOrderStatus())
                .content(JSONUtil.toJsonStr(orderDO))
                .remark(JSONUtil.toJsonStr(orderCreateDTO))
                .build());

        // 转换订单数据
        OrderVO orderVO = OrderConverter.convertTo(orderDO);
        // TODO 设置订单其他信息

        return orderVO;
    }

    @Override
    public OrderVO detailById(String orderId) {
        // TODO 业务逻辑校验

        // 删除
        OrderDO order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return OrderConverter.convertTo(order);
    }

    @Override
    public OrderVO detailByNo(String orderNo) {
        // TODO 业务逻辑校验

        // 获取订单信息
        OrderDO order = orderRepository.findByNo(orderNo)
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
    public Page<OrderVO> page(OrderQueryDTO orderQueryDTO) {
        // 构建分页数据
        Pageable pageable = orderQueryDTO.pageable();
        return OrderConverter.convertTo(orderRepository.findAll(pageable));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderVO confirmOrder(String orderId) {
        // 获取订单详情
        OrderDO orderDO = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("当前订单信息不存在"));
        // 修改订单状态
        orderDO.setOrderStatus(OrderStatusEnum.WAIT_PAID);
        // 修改订单数据
        orderRepository.save(orderDO);
        // 存储订单变化记录
        orderRecordService.save(OrderRecordDTO.builder()
                .orderNo(orderDO.getNo())
                .orderStatus(orderDO.getOrderStatus())
                .content(JSONUtil.toJsonStr(orderDO))
                .remark(String.format("接收到订单确认，订单号：%s", orderDO.getNo()))
                .build());
        return OrderConverter.convertTo(orderDO);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderVO prePayOrder(String orderNo) {
        // 获取订单详情
        OrderDO orderDO = orderRepository.findByNo(orderNo)
                .orElseThrow(() -> new BusinessException("当前订单信息不存在"));
        // 修改订单状态
        orderDO.setOrderStatus(OrderStatusEnum.PRE_PAID);
        // 修改订单数据
        orderRepository.save(orderDO);
        // 存储订单变化记录
        orderRecordService.save(OrderRecordDTO.builder()
                .orderNo(orderDO.getNo())
                .orderStatus(orderDO.getOrderStatus())
                .content(JSONUtil.toJsonStr(orderDO))
                .remark(String.format("订单已发起后端预支付逻辑，订单号：%s", orderDO.getNo()))
                .build());
        return OrderConverter.convertTo(orderDO);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean paySuccessHandler(String orderNo) {
        // 获取订单详情
        OrderDO orderDO = orderRepository.findByNo(orderNo)
                .orElseThrow(() -> new BusinessException("当前订单信息不存在"));
        // 修改订单状态
        orderDO.setOrderStatus(OrderStatusEnum.WAIT_SHIPPED);
        // 支付状态
        orderDO.setPayStatus(PayStatusEnum.PAY_YES);
        // 创建支付时间
        orderDO.setCompletePayAt(DateUtil.now());
        // 修改订单数据
        orderRepository.save(orderDO);
        // 存储订单变化记录
        orderRecordService.save(OrderRecordDTO.builder()
                .orderNo(orderDO.getNo())
                .orderStatus(orderDO.getOrderStatus())
                .content(JSONUtil.toJsonStr(orderDO))
                .remark(String.format("接收到支付成功回调函数：%s", orderNo))
                .build());
        return true;
    }

}