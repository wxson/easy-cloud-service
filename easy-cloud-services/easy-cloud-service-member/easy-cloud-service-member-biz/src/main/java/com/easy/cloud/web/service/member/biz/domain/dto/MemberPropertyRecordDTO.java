package com.easy.cloud.web.service.member.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.member.biz.domain.db.MemberPropertyRecordDO;
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
@ApiModel(value = "会员资产数据", description = "会员资产请求数据")
public class MemberPropertyRecordDTO implements IConverter<MemberPropertyRecordDO> {
    /**
     * 唯一标识
     */
    @ApiModelProperty(name = "id", value = "唯一标识")
    private Integer id;

    /**
     * 资产来源
     */
    @ApiModelProperty(name = "origin", value = "资产来源")
    private Integer origin;

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "userId", value = "用户ID")
    private String userId;

    /**
     * 最晚创建时间
     */
    @ApiModelProperty(name = "maxCreateAt", value = "最晚创建时间")
    private String maxCreateAt;

    /**
     * 最早创建时间
     */
    @ApiModelProperty(name = "minCreateAt", value = "最早创建时间")
    private String minCreateAt;

}