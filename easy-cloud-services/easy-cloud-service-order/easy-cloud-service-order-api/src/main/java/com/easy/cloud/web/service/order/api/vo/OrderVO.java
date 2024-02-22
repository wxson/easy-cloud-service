package com.easy.cloud.web.service.order.api.vo;

import com.easy.cloud.web.service.order.api.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.Integer;

/**
 * Order展示数据
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

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
     * 货币类型，交易使用的货币类型
     */
    private CurrencyTypeEnum currencyType;
    /**
     * 支付金额
     */
    private Integer amount;
    /**
     * 优惠金额
     */
    private Integer discountsAmount;
    /**
     * 售价
     */
    private Integer salesPrice;
    /**
     * 采购价
     */
    private Integer purchasePrice;
    /**
     * 原价
     */
    private Integer originalPrice;
    /**
     * 售后状态
     */
    private AftermarketStatusEnum aftermarketStatus;
    /**
     * 创建售后时间
     */
    private String createAftermarketAt;
    /**
     * 完成售后时间
     */
    private String completeAftermarketAt;
    /**
     * 评论状态
     */
    private CommentStatusEnum commentStatus;
    /**
     * 创建评论时间
     */
    private String createCommentAt;
    /**
     * 完成评论时间
     */
    private String completeCommentAt;
    /**
     * 完成状态
     */
    private FinishStatusEnum finishStatus;
    /**
     * 完成时间
     */
    private String finishAt;
    /**
     * 物流状态
     */
    private LogisticsStatusEnum logisticsStatus;
    /**
     * 创建物流时间
     */
    private String createLogisticsAt;
    /**
     * 完成物流时间
     */
    private String completeLogisticsAt;
    /**
     * 订单状态
     */
    private OrderStatusEnum orderStatus;
    /**
     * 创建订单时间
     */
    private String createOrderAt;
    /**
     * 完成订单时间
     */
    private String completeOrderAt;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 创建支付时间
     */
    private String createPayAt;
    /**
     * 完成支付时间
     */
    private String completePayAt;
}