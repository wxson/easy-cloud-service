package com.easy.cloud.web.service.order.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.order.biz.domain.db.OrderDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "订单查询", description = "订单查询参数")
public class OrderDTO implements IConverter<OrderDO> {
    /**
     * 用户ID
     */
    @NotBlank(message = "商品编号不能为空")
    @ApiModelProperty(name = "goodsNo", value = "用户ID")
    private String goodsNo;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    @ApiModelProperty(name = "goodsNum", value = "商品数量")
    private Integer goodsNum;
}