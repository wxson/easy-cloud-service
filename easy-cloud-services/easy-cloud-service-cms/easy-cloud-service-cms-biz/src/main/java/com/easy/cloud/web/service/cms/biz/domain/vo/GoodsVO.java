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
@ApiModel(value = "商品数据", description = "商品返回数据")
public class GoodsVO implements IConvertProxy {
    /**
     * 商品ID
     */
    @ApiModelProperty(name = "id", value = "商品ID")
    private Integer id;

    /**
     * 商品参加的活动
     */
    @ApiModelProperty(name = "action", value = "商品参加的活动")
    private Integer action;

    /**
     * 商品唯一编号
     */
    @ApiModelProperty(name = "no", value = "商品唯一编号")
    private String no;

    /**
     * 商品名称
     */
    @ApiModelProperty(name = "name", value = "商品名称")
    private String name;

    /**
     * 图片
     */
    @ApiModelProperty(name = "picture", value = "图片")
    private String picture;

    /**
     * 是否已支付
     */
    @ApiModelProperty(name = "paidOff", value = "是否已支付")
    private Integer paidOff;

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

    /**
     * 创建人
     */
    @ApiModelProperty(name = "creatorAt", value = "创建人")
    private String creatorAt;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createAt", value = "创建时间")
    private String createAt;

    /**
     * 更新用户
     */
    @ApiModelProperty(name = "updaterAt", value = "更新用户")
    private String updaterAt;

    /**
     * 更新时间
     */
    @ApiModelProperty(name = "updateAt", value = "更新时间")
    private String updateAt;
}