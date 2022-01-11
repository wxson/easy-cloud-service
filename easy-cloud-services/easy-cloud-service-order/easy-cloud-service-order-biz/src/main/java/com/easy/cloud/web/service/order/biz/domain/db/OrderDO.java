package com.easy.cloud.web.service.order.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.order.biz.domain.vo.OrderVO;
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
@TableName("db_order")
public class OrderDO implements IConverter<OrderVO> {
    /**
     * 文档ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
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
    private Integer currencyType;
    /**
     * 支付金额
     */
    private BigDecimal amount;
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
    private Integer commentStatus;
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
    private Integer finishStatus;
    /**
     * 完成时间
     */
    private String finishAt;
    /**
     * 物流状态
     */
    private Integer logisticsStatus;
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
    private Integer orderStatus;
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