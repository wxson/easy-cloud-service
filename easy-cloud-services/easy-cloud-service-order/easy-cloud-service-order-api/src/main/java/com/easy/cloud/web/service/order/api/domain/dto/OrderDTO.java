package com.easy.cloud.web.service.order.api.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单参数
 *
 * @author GR
 * @date 2021-11-12 14:44
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class OrderDTO {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订单唯一编号
     */
    private String orderNo;
    /**
     * 商品唯一编号
     */
    private String goodsNo;
    /**
     * 商品数量
     */
    private Integer goodsNum;
    /**
     * 最大创建时间
     */
    private String maxCreateAt;
    /**
     * 最小创建时间
     */
    private String minCreateAt;
    /**
     * 订单状态
     * <p>
     * NEW(1, "新订单"),
     * OUT_DB_ERROR(2, "出库失败"),
     * CONFIRM(3, "已确认"),
     * PAID_OFF(4, "已付款"),
     * SHIPPED(5, "已发货"),
     * ROG(6, "已收货"),
     * COMPLETE(7, "已完成"),
     * CANCELLED(8, "已取消"),
     * AFTER_SERVICE(9, "售后中"),
     * </p>
     */
    private Integer orderStatus;
}
