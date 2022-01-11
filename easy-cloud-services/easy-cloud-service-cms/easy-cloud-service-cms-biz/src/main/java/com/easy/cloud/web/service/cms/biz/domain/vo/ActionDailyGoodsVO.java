package com.easy.cloud.web.service.cms.biz.domain.vo;

import com.easy.cloud.web.component.core.service.IConvertProxy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "每日领取商品数据", description = "每日领取商品数据")
public class ActionDailyGoodsVO implements IConvertProxy {
    /**
     * 商品唯一编号
     */
    @ApiModelProperty(name = "goodsNo", value = "商品唯一编号")
    private String goodsNo;

    /**
     * 是否支持进一步操作
     */
    @ApiModelProperty(name = "handle", value = "是否支持进一步操作")
    private Boolean handle;

    /**
     * 商品类型
     */
    @ApiModelProperty(name = "goodsType", value = "商品类型")
    private Integer goodsType;

    /**
     * 货币类型，交易使用的货币类型
     */
    @ApiModelProperty(name = "currencyType", value = "货币类型，交易使用的货币类型")
    private Integer currencyType;

    /**
     * 售价
     */
    @ApiModelProperty(name = "salesPrice", value = "售价")
    private BigDecimal salesPrice;

    /**
     * 原价
     */
    @ApiModelProperty(name = "originalPrice", value = "原价")
    private BigDecimal originalPrice;

    /**
     * 钻石数量
     */
    @ApiModelProperty(name = "diamondNum", value = "钻石数量")
    private Integer diamondNum;

    /**
     * 金币数量
     */
    @ApiModelProperty(name = "goldCoinNum", value = "金币数量")
    private Integer goldCoinNum;

    /**
     * 点券数量
     */
    @ApiModelProperty(name = "couponNum", value = "点券数量")
    private Integer couponNum;

    /**
     * 活跃值
     */
    @ApiModelProperty(name = "aliveness", value = "活跃值")
    private Integer aliveness;
}