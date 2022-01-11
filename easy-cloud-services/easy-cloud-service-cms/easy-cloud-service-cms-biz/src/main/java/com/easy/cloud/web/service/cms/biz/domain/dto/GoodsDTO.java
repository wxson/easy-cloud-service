package com.easy.cloud.web.service.cms.biz.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品参数", description = "商品请求参数")
public class GoodsDTO implements IConverter<GoodsDO> {
    /**
     * 商品ID
     */
    @TableId
    @ApiModelProperty(name = "id", value = "商品ID")
    private Integer id;

    /**
     * 商品唯一编号
     */
    @ApiModelProperty(name = "no", value = "商品编码")
    private String no;

    /**
     * 商品名称
     */
    @ApiModelProperty(required = true, name = "name", value = "商品名称")
    private String name;

    /**
     * 图片
     */
    @ApiModelProperty(required = true, name = "picture", value = "商品图片")
    private String picture;

    /**
     * 商品参加的活动
     */
    @ApiModelProperty(required = false, name = "action", value = "商品参加的活动")
    private Integer action;

    /**
     * 商品类型
     */
    @ApiModelProperty(required = true, name = "goodsType", value = "商品类型:     DIAMOND(1, \"钻石\"),\n" +
            "    GOLD_COIN(2, \"金币\"),\n" +
            "    COUPON(3, \"点券\"),\n" +
            "    TABLECLOTH(4, \"桌布\"),\n" +
            "    CARD_BACK(5, \"牌背\"),\n" +
            "    DIAMOND_GOLD_COIN(6, \"钻石+金币\"),\n" +
            "    DIAMOND_COUPON(7, \"钻石+点券\"),\n" +
            "    GOLD_COIN_COUPON(8, \"金币+点券\"),\n" +
            "    DIAMOND_GOLD_COIN_COUPON(9, \"钻石+金币+点券\"),")
    private Integer goodsType;

    /**
     * 货币类型，交易使用的货币类型
     */
    @ApiModelProperty(required = true, name = "currencyType", value = "货币类型，交易使用的货币类型:     CNY(1, \"人民币\"),\n" +
            "    GOLD_COIN(2, \"金币\"),\n" +
            "    DIAMOND(3, \"钻石\"),\n" +
            "    COUPON(4, \"点券\"), ")
    private Integer currencyType;

    /**
     * 售价
     */
    @ApiModelProperty(name = "salesPrice", value = "售价")
    private BigDecimal salesPrice;

    /**
     * 采购价
     */
    @ApiModelProperty(name = "purchasePrice", value = "采购价")
    private BigDecimal purchasePrice;

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
     * 每日免费领取商品
     */
    @ApiModelProperty(name = "dailyGoodsNo", value = "每日免费领取商品")
    private String dailyGoodsNo;

    /**
     * 触发奖励值
     */
    @ApiModelProperty(name = "triggerValue", value = "触发奖励值")
    private Integer triggerValue;

    /**
     * 内容描述
     */
    @ApiModelProperty(name = "content", value = "内容描述")
    private String content;

    /**
     * 备注
     */
    @ApiModelProperty(name = "remark", value = "备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;

    /**
     * 状态 0 启用 1 禁用
     */
    @ApiModelProperty(name = "status", value = "状态 0 启用 1 禁用")
    private Integer status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    @ApiModelProperty(name = "deleted", value = "是否删除 0 未删除 1 已删除")
    private Integer deleted;
}