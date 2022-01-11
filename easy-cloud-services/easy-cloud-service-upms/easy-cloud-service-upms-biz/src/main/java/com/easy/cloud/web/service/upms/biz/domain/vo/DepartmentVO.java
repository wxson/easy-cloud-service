package com.easy.cloud.web.service.upms.biz.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Fast Java
 * @date 2021-04-06
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "部门数据", description = "部门返回数据")
public class DepartmentVO {
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
     * 上级部门名称
     */
    @ApiModelProperty(name = "parentName", value = "上级部门名称")
    private String parentName;
    /**
     * 部门名称
     */
    @ApiModelProperty(name = "name", value = "部门名称")
    private String name;
    /**
     * 排序
     */
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;
    /**
     * 父级部门ID
     */
    @ApiModelProperty(name = "parentId", value = "父级部门ID")
    private String parentId;
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