package com.easy.cloud.web.service.upms.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.db.DepartmentDO;
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
@ApiModel(value = "部门参数", description = "部门请求参数")
public class DepartmentDTO implements IConverter<DepartmentDO> {
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
     * 上级部门ID
     */
    @ApiModelProperty(name = "parentId", value = "上级部门ID")
    private String parentId;
}