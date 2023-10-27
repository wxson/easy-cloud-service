package com.easy.cloud.web.service.cms.biz.domain.vo;

import cn.hutool.json.JSONObject;
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
@ApiModel(value = "商品数据", description = "商品返回数据")
public class ActionVO implements IConvertProxy {
    /**
     * 商品唯一编号
     */
    @ApiModelProperty(name = "goodsNo", value = "商品唯一编号")
    private String goodsNo;
    /**
     * 商品名称
     */
    @ApiModelProperty(name = "goodsName", value = "商品名称")
    private String goodsName;
    /**
     * 是否支持进一步操作
     */
    @ApiModelProperty(name = "handle", value = "是否支持进一步操作")
    private Boolean handle;
    /**
     * 禁止
     */
    @ApiModelProperty(name = "forbid", value = "禁止")
    private Boolean forbid;
    /**
     * 是否免费
     */
    @ApiModelProperty(name = "free", value = "是否免费")
    private Boolean free;
    /**
     * 剩余免费天数
     */
    @ApiModelProperty(name = "freeDay", value = "剩余免费天数")
    private Long freeDay;
    /**
     * 总金额
     */
    @ApiModelProperty(name = "totalAmount", value = "总金额")
    private Long totalAmount;
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

    /**
     * 活动奖励触发值
     */
    @ApiModelProperty(name = "triggerValue", value = "活动奖励触发值")
    private Integer triggerValue;

    /**
     * 当前触发值
     */
    @ApiModelProperty(name = "currentValue", value = "当前触发值")
    private Integer currentValue;


    /**
     * 每日领取商品
     */
    @ApiModelProperty(name = "dailyGoods", value = "每日领取商品")
    private ActionDailyGoodsVO dailyGoods;

    /**
     * 特殊数据
     */
    @ApiModelProperty(name = "specialData", value = "特殊数据")
    private JSONObject specialData;

}