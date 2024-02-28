package com.easy.cloud.web.service.upms.api.vo;

import com.easy.cloud.web.service.upms.api.enums.TenantTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Tenant展示数据
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TenantVO", description = "租户返回参数")
public class TenantVO {

    /**
     * 文档ID
     */
    @ApiModelProperty(value = "文档ID", required = false)
    private String id;
    /**
     * 账户ID
     */
    @ApiModelProperty(value = "账户ID", required = false)
    private String accountId;
    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称", required = true)
    private String name;

    /**
     * 租户类型（企业和个人）
     */
    @ApiModelProperty(value = "租户类型（企业和个人）", required = false)
    private TenantTypeEnum type;
    /**
     * 国家
     */
    @ApiModelProperty(value = "国家", required = false)
    private String country;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份", required = true)
    private String province;
    /**
     * 城市
     */
    @ApiModelProperty(value = "城市", required = true)
    private String city;
    /**
     * 地区
     */
    @ApiModelProperty(value = "地区", required = false)
    private String district;
    /**
     * 街道
     */
    @ApiModelProperty(value = "街道", required = false)
    private String street;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", required = true)
    private String address;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人", required = false)
    private String leader;
    /**
     * 负责人身份证
     */
    @ApiModelProperty(value = "负责人身份证", required = false)
    private String idCard;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", required = false)
    private String tel;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", required = false)
    protected Date startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", required = false)
    protected Date endDate;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = false)
    private String remark;
}