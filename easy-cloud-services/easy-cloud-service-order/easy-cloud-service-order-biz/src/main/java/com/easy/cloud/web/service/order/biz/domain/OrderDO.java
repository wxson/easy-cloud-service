package com.easy.cloud.web.service.order.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.component.pay.enums.PayStatusEnum;
import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
import com.easy.cloud.web.service.order.api.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Order 持久类
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_order")
public class OrderDO extends BaseEntity {

    /**
     * 订单编号
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '订单编号'")
    private String no;
    /**
     * 商品唯一编码
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '商品唯一编码'")
    private String goodsNo;
    /**
     * 商品名称
     */
    @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '商品名称'")
    private String goodsName;
    /**
     * 商品内容
     */
    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '商品内容'")
    private String goodsContent;
    /**
     * 商品数量
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '1' COMMENT '商品数量'")
    private Integer goodsNum;
    /**
     * 订单类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'GOODS' COMMENT '订单类型'")
    private OrderTypeEnum orderType;
    /**
     * 货币类型，交易使用的货币类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'CNY' COMMENT '货币类型'")
    private CurrencyTypeEnum currencyType;
    /**
     * 订单来源：1：APP；2：PC；3：小程序
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'APP' COMMENT '订单来源：1：APP；2：PC；3：小程序'")
    private SourceTypeEnum sourceType;
    /**
     * 支付金额
     */
    @Column(columnDefinition = "BIGINT NOT NULL DEFAULT '0.00' COMMENT '支付金额'")
    private BigDecimal amount;
    /**
     * 优惠金额
     */
    @Column(columnDefinition = "BIGINT NOT NULL DEFAULT '0.00' COMMENT '优惠金额'")
    private BigDecimal discountsAmount;
    /**
     * 售价
     */
    @Column(columnDefinition = "BIGINT NOT NULL DEFAULT '0.00' COMMENT '售价'")
    private BigDecimal salesPrice;
    /**
     * 采购价
     */
    @Column(columnDefinition = "BIGINT NOT NULL DEFAULT '0.00' COMMENT '采购价'")
    private BigDecimal purchasePrice;
    /**
     * 原价
     */
    @Column(columnDefinition = "BIGINT NOT NULL DEFAULT '0.00' COMMENT '原价'")
    private BigDecimal originalPrice;
    /**
     * 售后状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'NOT_APPLY' COMMENT '售后状态'")
    private AftermarketStatusEnum aftermarketStatus;
    /**
     * 创建售后时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '创建售后时间'")
    private String createAftermarketAt;
    /**
     * 完成售后时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '完成售后时间'")
    private String completeAftermarketAt;
    /**
     * 评论状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'UN_COMMENT' COMMENT '评论状态'")
    private CommentStatusEnum commentStatus;
    /**
     * 创建评论时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '创建评论时间'")
    private String createCommentAt;
    /**
     * 完成评论时间:到期自动评论
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '完成评论时间:到期自动评论'")
    private String completeCommentAt;
    /**
     * 完成状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'UNFINISHED' COMMENT '完成状态'")
    private FinishStatusEnum finishStatus;
    /**
     * 完成时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '完成时间'")
    private String finishAt;
    /**
     * 物流状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'SHIP_NO' COMMENT '物流状态'")
    private LogisticsStatusEnum logisticsStatus;
    /**
     * 创建物流时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '创建物流时间'")
    private String createLogisticsAt;
    /**
     * 物流单号
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '物流单号'")
    private String logisticsNo;
    /**
     * 完成物流时间：确认收货时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '完成物流时间：确认收货时间'")
    private String completeLogisticsAt;
    /**
     * 订单状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'NEW' COMMENT '订单状态'")
    private OrderStatusEnum orderStatus;
    /**
     * 发票ID
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '发票ID'")
    private String invoiceId;
    /**
     * 完成订单时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '完成订单时间'")
    private String completeOrderAt;
    /**
     * 支付类型：支付后才能知道支付类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) COMMENT '支付类型'")
    private PayTypeEnum payType;
    /**
     * 支付状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT 'PAY_NO' COMMENT '支付状态'")
    private PayStatusEnum payStatus;
    /**
     * 完成支付时间
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '完成支付时间'")
    private String completePayAt;
    /**
     * 交易号：即支付平台交易订单编码
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '交易号：即支付平台交易订单编码'")
    private String tradeNo;
}