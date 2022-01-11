package com.easy.cloud.web.service.cms.api.domain.vo;

import com.easy.cloud.web.component.core.service.IConvertProxy;
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
public class GoodsVO implements IConvertProxy {
    /**
     * 商品ID
     */
    private Integer id;

    /**
     * 商品参加的活动
     */
    private Integer action;

    /**
     * 商品唯一编号
     */
    private String no;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 图片
     */
    private String picture;

    /**
     * 商品类型
     */
    private Integer goodsType;

    /**
     * 货币类型，交易使用的货币类型
     */
    private Integer currencyType;

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
     * 钻石数量
     */
    private Integer diamondNum;

    /**
     * 金币数量
     */
    private Integer goldCoinNum;

    /**
     * 点券数量
     */
    private Integer couponNum;

    /**
     * 内容描述
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer deleted;
}