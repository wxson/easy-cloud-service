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

/**
 * Department展示数据
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeptVO", description = "部门回参")
public class DeptVO {

    /**
     * 文档ID
     */
    @ApiModelProperty(value = "", required = false)
    private String id;
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
}