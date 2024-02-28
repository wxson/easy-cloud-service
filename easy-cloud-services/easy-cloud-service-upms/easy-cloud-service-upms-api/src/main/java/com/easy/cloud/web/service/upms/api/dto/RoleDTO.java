package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * Role请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleDTO", description = "角色入参")
public class RoleDTO implements IConverter {

    /**
     * 文档ID，必须保证角色ID的全局唯一性
     */
    @ApiModelProperty(value = "文档ID", required = false)
    private String id;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码", required = true)
    private String code;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = false)
    private String remark;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;
    /**
     * 权限列表
     */
    @ApiModelProperty(value = "权限列表", required = false)
    private Set<String> menuIds;
}