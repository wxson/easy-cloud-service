package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Department请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeptDTO", description = "部门入参")
public class DeptDTO implements IConverter {

    /**
     * 文档ID
     */
    @ApiModelProperty(value = "文档ID", required = false)
    private String id;
    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身 一个企业、一个单位或一所学校只能有一个租户
     */
    @ApiModelProperty(value = "租户ID", required = false)
    private String tenantId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", required = false)
    private String name;
    /**
     * 上级部门
     */
    @ApiModelProperty(value = "上级部门", required = false)
    private String parentId;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人", required = false)
    private String leader;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "", required = false)
    private String tel;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;
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
}