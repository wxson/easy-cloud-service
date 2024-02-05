package com.easy.cloud.web.service.order.api.dto;

import com.easy.cloud.web.service.order.api.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Order 创建请求数据
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
    /**
     * 订单类型
     */
    private OrderTypeEnum orderType;
    /**
     * 商品唯一编码
     */
    private String goodsNo;
    /**
     * 商品数量
     */
    private Integer goodsNum;
}