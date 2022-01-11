package com.easy.cloud.web.service.order.biz.domain.query;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "订单查询", description = "订单查询参数")
public class OrderQuery {
    /**
     * 用户ID
     */
    @TableField("user_id")
    @ApiModelProperty(name = "userId", value = "用户ID")
    private String userId;
    /**
     * 订单编号
     */
    @TableField("no")
    @ApiModelProperty(name = "no", value = "订单编号")
    private String no;
    /**
     * 商品编号
     */
    @TableField("goods_no")
    @ApiModelProperty(name = "goodsNo", value = "商品编号")
    private String goodsNo;
    /**
     * 最大创建时间
     */
    @ApiModelProperty(name = "maxCreateAt", value = "最大创建时间")
    private String maxCreateAt;
    /**
     * 最小创建时间
     */
    @ApiModelProperty(name = "minCreateAt", value = "最小创建时间")
    private String minCreateAt;
    /**
     * 订单状态
     */
    @TableField("order_status")
    @ApiModelProperty(name = "orderStatus", value = "订单状态")
    private Integer orderStatus;

    /**
     * 支付状态
     */
    @TableField("pay_status")
    @ApiModelProperty(name = "payStatus", value = "支付状态状态")
    private Integer payStatus;

    /**
     * 支付货币
     */
    @TableField("currency_type")
    @ApiModelProperty(name = "currencyType", value = "支付货币")
    private Integer currencyType;
}