package com.easy.cloud.web.service.cms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.cms.biz.domain.vo.GoodsVO;
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
@TableName("db_goods")
public class GoodsDO implements IConverter<GoodsVO> {
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 商品参加的活动
     */
    private Integer action;

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
     * 活跃值
     */
    private Integer aliveness;

    /**
     * 每日免费领取商品
     */
    private String dailyGoodsNo;

    /**
     * 触发奖励值
     */
    private Integer triggerValue;

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

    /**
     * 创建人
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}