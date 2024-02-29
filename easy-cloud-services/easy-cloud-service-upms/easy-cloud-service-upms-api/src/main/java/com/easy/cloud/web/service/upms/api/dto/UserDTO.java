package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "UserDTO", description = "用户入参")
public class UserDTO implements IConverter {

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
     * 微信union Id
     */
    @ApiModelProperty(value = "微信union_id", required = false)
    private String unionId;
    /**
     * 苹果ID
     */
    @ApiModelProperty(value = "苹果ID", required = false)
    private String appleId;
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
     * 昵称
     */
    @ApiModelProperty(value = "昵称", required = false)
    private String nickName;
    /**
     * 真实名字
     */
    @ApiModelProperty(value = "真实名字", required = false)
    private String realName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = false)
    private String password;
    /**
     * 性别： 0 未知的性别 1 男 2 女 9 未说明的性别
     */
    @ApiModelProperty(value = "性别： 0 未知的性别 1 男 2 女 9 未说明的性别", required = false)
    private GenderEnum gender;
    /**
     * 用户名、用户账号
     */
    @ApiModelProperty(value = "用户名、用户账号", required = false)
    private String userName;
    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证", required = false)
    private String idCard;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = false)
    private String tel;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = false)
    private String avatar;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;
    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID", required = false)
    private Set<String> deptIds;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码", required = false)
    private Set<String> roleCodes;
}