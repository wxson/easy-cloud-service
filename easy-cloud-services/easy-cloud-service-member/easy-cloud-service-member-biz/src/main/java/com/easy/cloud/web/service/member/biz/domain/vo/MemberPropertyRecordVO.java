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
public class MemberPropertyRecordVO implements IConvertProxy {
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
     * 创建用户
     */
    @ApiModelProperty(name = "creatorAt", value = "创建用户")
    private String creatorAt;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createAt", value = "创建时间")
    private String createAt;
}