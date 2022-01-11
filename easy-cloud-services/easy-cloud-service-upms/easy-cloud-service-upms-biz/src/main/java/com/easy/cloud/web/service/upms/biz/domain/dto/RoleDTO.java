package com.easy.cloud.web.service.upms.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.db.RelationRolePermissionDO;
import com.easy.cloud.web.service.upms.biz.domain.db.RoleDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Fast Java
 * @date 2021-04-01
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色参数", description = "角色请求参数")
public class RoleDTO implements IConverter<RoleDO> {
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
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(name = "name", value = "角色名称")
    private String name;
    /**
     * 权限列表
     */
    @ApiModelProperty(name = "permissionList", value = "权限列表")
    private List<RelationRolePermissionDO> permissionList;
}