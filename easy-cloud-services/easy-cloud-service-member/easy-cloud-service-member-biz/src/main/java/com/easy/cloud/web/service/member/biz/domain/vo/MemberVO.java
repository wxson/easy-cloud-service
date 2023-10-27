package com.easy.cloud.web.service.member.biz.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员资产信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "会员资产记录数据", description = "会员资产返回数据")
public class MemberVO implements IConvertProxy {

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
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 用户昵称
     */
    @ApiModelProperty(name = "nickName", value = "用户昵称")
    private String nickName;

    /**
     * 用户账号
     */
    @ApiModelProperty(name = "account", value = "用户账号")
    private String account;

    /**
     * 用户头像
     */
    @ApiModelProperty(name = "avatar", value = "用户头像")
    private String avatar;

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
     * 当前的总充值钻石数
     */
    @ApiModelProperty(name = "currentRechargeTotalDiamondNum", value = "当前的总充值钻石数")
    private Long currentRechargeTotalDiamondNum;

    /**
     * 下一VIP钻石钻石数
     */
    @ApiModelProperty(name = "nextVipLevelDiamondNum", value = "下一VIP钻石钻石数")
    private Long nextVipLevelDiamondNum;

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

    /**
     * 创建用户
     */
    @ApiModelProperty(name = "creatorAt", value = "创建用户")
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