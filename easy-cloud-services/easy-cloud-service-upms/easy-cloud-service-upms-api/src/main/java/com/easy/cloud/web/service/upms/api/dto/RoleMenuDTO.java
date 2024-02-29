package com.easy.cloud.web.service.upms.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * User请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleMenuDTO", description = "角色菜单入参")
public class RoleMenuDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = false)
    private String roleId;


    /**
     * 权限列表
     */
    @ApiModelProperty(value = "权限列表", required = false)
    private Set<String> menuIds;

}