package com.easy.cloud.web.service.upms.api.vo;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * Role展示数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleVO", description = "角色回参")
public class RoleVO {

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
     * 状态 0 启用 1 禁用
     */
    @ApiModelProperty(value = "状态 0 启用 1 禁用", required = false)
    private StatusEnum status;
    /**
     * 是否删除 0 未删除 1 已删除
     */
    @ApiModelProperty(value = "是否删除 0 未删除 1 已删除", required = false)
    private DeletedEnum deleted;
    /**
     * 权限列表
     */
    @ApiModelProperty(value = "权限列表", required = false)
    private Set<String> menuIds;
}