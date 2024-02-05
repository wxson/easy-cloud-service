package com.easy.cloud.web.service.order.api.dto;

import com.easy.cloud.web.component.core.entity.BasePage;
import com.easy.cloud.web.service.order.api.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Order 查询数据
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryDTO extends BasePage {

    /**
     * 订单编号
     */
    private String no;

    /**
     * 售后状态
     */
    private AftermarketStatusEnum aftermarketStatus;

    /**
     * 评论状态
     */
    private CommentStatusEnum commentStatus;

    /**
     * 完成状态
     */
    private FinishStatusEnum finishStatus;

    /**
     * 物流状态
     */
    private LogisticsStatusEnum logisticsStatus;

    /**
     * 订单状态
     */
    private OrderStatusEnum orderStatus;

    /**
     * 支付状态
     */
    private PayStatusEnum payStatus;
}