package com.easy.cloud.web.service.member.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.db.MemberStorageDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员仓库信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "会员仓库数据", description = "会员仓库请求数据")
public class MemberStorageDTO implements IConverter<MemberStorageDO> {
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
     * 金币
     */
    @ApiModelProperty(name = "amount", value = "金币")
    private Long amount;

    /**
     * 状态 0 启用 1 禁用
     */
    @ApiModelProperty(name = "status", value = "状态 0 启用 1 禁用")
    private Integer status;
}