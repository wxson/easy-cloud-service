package com.easy.cloud.web.service.upms.biz.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Fast Java
 * @date 2021-04-01
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "角色数据", description = "角色返回数据")
public class RoleVO implements Serializable {
    /**
     * 文档ID
     */
    @ApiModelProperty(name = "id", value = "文档ID")
    private String id;
    /**
     * 租户ID
     */
    @ApiModelProperty(name = "tenantId", value = "租户ID")
    private String tenantId;
    /**
     * 角色名称
     */
    @ApiModelProperty(name = "name", value = "角色名称")
    private String name;
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
     * 更新时间
     */
    @ApiModelProperty(name = "updateAt", value = "更新时间")
    private String updateAt;
}