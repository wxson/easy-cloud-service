package com.easy.cloud.web.service.order.biz.service.factory;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.pay.enums.PayStatusEnum;
import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.api.enums.*;
import com.easy.cloud.web.service.order.biz.constants.OrderConstants;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import com.easy.cloud.web.service.order.biz.service.factory.order.GoodsOrderGenerateHandle;
import com.easy.cloud.web.service.order.biz.service.factory.order.IOrderGenerateHandle;
import com.easy.cloud.web.service.order.biz.service.factory.order.RechargeOrderGenerateHandle;
import com.easy.cloud.web.service.order.biz.service.factory.order.TransferOrderGenerateHandle;
import lombok.experimental.UtilityClass;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单生成逻辑
 *
 * @author GR
 * @date 2024/2/4 20:04
 */
@UtilityClass
public class OrderGenerateFactory {

    private Map<OrderTypeEnum, Class<? extends IOrderGenerateHandle>> orderGenerateMaps;

    static {
        orderGenerateMaps = new HashMap<>();
        orderGenerateMaps.put(OrderTypeEnum.GOODS, GoodsOrderGenerateHandle.class);
        orderGenerateMaps.put(OrderTypeEnum.RECHARGE, RechargeOrderGenerateHandle.class);
        orderGenerateMaps.put(OrderTypeEnum.TRANSFER, TransferOrderGenerateHandle.class);
    }

    /**
     * 根据订单类型生成订单对象
     *
     * @param orderType      订单类型
     * @param orderCreateDTO 订单创建信息
     * @return
     */
    public OrderDO generateOrder(OrderTypeEnum orderType, OrderCreateDTO orderCreateDTO) {
        // 默认商品订单
        orderType = Objects.isNull(orderType) ? OrderTypeEnum.GOODS : orderType;
        // 根据订单类型生成对应的订单数据
        IOrderGenerateHandle orderGenerateHandle = Optional
                .ofNullable(SpringContextHolder.getBean(orderGenerateMaps.get(orderType)))
                .orElseThrow(() -> new BusinessException("获取订单生成器失败"));
        // 根据生成器生成对应的订单信息
        OrderDO orderDO = orderGenerateHandle.generateOrder(orderCreateDTO);
        // 设置订单号
        orderDO.setNo(generateOrderNo(orderDO));
        // 新订单
        orderDO.setOrderStatus(OrderStatusEnum.WAI_CONFIRM);
        // 待付款
        orderDO.setPayStatus(PayStatusEnum.PAY_NO);
        // 评论状态
        orderDO.setCommentStatus(CommentStatusEnum.UN_COMMENT);
        // 物流状态
        orderDO.setLogisticsStatus(LogisticsStatusEnum.SHIP_NO);
        // 售后状态
        orderDO.setAftermarketStatus(AftermarketStatusEnum.NOT_APPLY);
        // 订单未完成
        orderDO.setFinishStatus(FinishStatusEnum.UNFINISHED);
        return orderDO;
    }

    /**
     * 生成18位订单编号:8位日期+2位订单类型+2位支付方式+6位以上自增id
     *
     * @param order 订单数据
     * @return 返回订单号
     */
    private String generateOrderNo(OrderDO order) {
        StringBuilder builder = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 获取redis 订单自增ID key
        String key = String.format(OrderConstants.ORDER_NO_INCR_ID_KEY, date);
        // Redis服务
        RedisTemplate redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);
        // 获取自增ID
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        builder.append(date)
                .append(String.format("%02d", order.getOrderType().getCode()))
                .append(String.format("%02d", order.getPayType().getCode()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            builder.append(String.format("%06d", increment));
        } else {
            builder.append(incrementStr);
        }
        return builder.toString();
    }
}

