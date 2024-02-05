package com.easy.cloud.web.service.order.api.vo;

import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OrderRecord展示数据
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecordVO {

    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单状态
     */
    private OrderStatusEnum orderStatus;
    /**
     * 订单内容
     */
    private String content;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 订单操作员
     */
    private String operator;
}