package com.easy.cloud.web.service.order.biz.domain.vo;

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
public class OrderVO implements IConvertProxy {
    /**
     * 预支付ID
     */
    private String prePayId;
    /**
     * 文档ID
     */
    private String id;
    /**
     * 订单编号
     */
    private String no;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品唯一编码
     */
    private String goodsNo;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品内容
     */
    private String goodsContent;
    /**
     * 商品数量
     */
    private Integer goodsNum;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 货币类型，交易使用的货币类型
     */
    private Integer currencyType;
    /**
     * 优惠金额
     */
    private BigDecimal discountsAmount;
    /**
     * 售价
     */
    private BigDecimal salesPrice;
    /**
     * 采购价
     */
    private BigDecimal purchasePrice;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 售后状态
     */
    private Integer aftermarketStatus;
    /**
     * 评论状态
     */
    private Integer commentStatus;
    /**
     * 完成状态
     */
    private Integer finishStatus;
    /**
     * 物流状态
     */
    private Integer logisticsStatus;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新用户
     */
    private String updaterAt;
    /**
     * 更新时间
     */
    private String updateAt;
}