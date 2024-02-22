package com.easy.cloud.web.service.order.api.dto;

import com.easy.cloud.web.service.order.api.enums.OrderTypeEnum;
import com.easy.cloud.web.service.order.api.enums.SourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
     * 订单来源
     */
    private SourceTypeEnum sourceType;
    /**
     * 商品唯一编码
     */
    @NotBlank(message = "商品编号不能为空")
    private String goodsNo;
    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    private Integer goodsNum;
}