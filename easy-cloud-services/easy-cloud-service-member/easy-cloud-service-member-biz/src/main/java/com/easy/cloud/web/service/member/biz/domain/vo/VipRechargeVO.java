package com.easy.cloud.web.service.member.biz.domain.vo;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.db.VipRechargeDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 会员等级充值信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "会员等级充值数据", description = "会员等级充值请求数据")
public class VipRechargeVO implements IConverter<VipRechargeDO> {
    /**
     * 唯一标识
     */
    @ApiModelProperty(name = "id", value = "唯一标识")
    private Integer id;

    /**
     * 累计充值
     */
    @ApiModelProperty(name = "totalRecharge", value = "累计充值")
    private BigDecimal totalRecharge;

    /**
     * VIP等级
     */
    @ApiModelProperty(name = "vipLevel", value = "VIP等级")
    private Integer vipLevel;

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