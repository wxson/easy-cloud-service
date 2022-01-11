package com.easy.cloud.web.service.member.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.db.MemberDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 会员资产信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "会员数据", description = "会员请求数据")
public class MemberDTO implements IConverter<MemberDO> {
    /**
     * 唯一标识
     */
    @ApiModelProperty(name = "id", value = "唯一标识")
    private Integer id;

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "userId", value = "用户ID")
    private String userId;

    /**
     * 资产来源
     */
    @ApiModelProperty(name = "origin", value = "资产来源")
    private Integer origin;

    /**
     * 订单编号
     */
    @ApiModelProperty(name = "orderNo", value = "订单编号")
    private String orderNo;

    /**
     * 用户昵称
     */
    @ApiModelProperty(name = "nickName", value = "用户昵称")
    private String nickName;

    /**
     * 充值金额
     */
    @ApiModelProperty(name = "recharge", value = "充值金额")
    private BigDecimal recharge;

    /**
     * 金币
     */
    @ApiModelProperty(name = "amount", value = "金币")
    private Long amount;

    /**
     * 钻石
     */
    @ApiModelProperty(name = "diamond", value = "钻石")
    private Long diamond;

    /**
     * 点券
     */
    @ApiModelProperty(name = "coupon", value = "点券")
    private Long coupon;

    /**
     * 状态 0 启用 1 禁用
     */
    @ApiModelProperty(name = "status", value = "状态 0 启用 1 禁用")
    private Integer status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    @ApiModelProperty(name = "deleted", value = "是否删除 0 未删除 1 已删除")
    private Boolean deleted;
}