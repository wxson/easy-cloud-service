package com.easy.cloud.web.service.upms.biz.domain.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.db.UserDO;
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
 * @date 2021-03-16
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户参数", description = "用户请求参数")
public class UserDTO implements IConverter<UserDO> {
    /**
     * 文档ID
     */
    @ApiModelProperty(name = "id", value = "文档ID")
    private String id;
    /**
     * Oauth2.0 授权登录code
     */
    @ApiModelProperty(name = "code", value = "Oauth2.0 授权登录code")
    private String code;
    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身
     */
    @ApiModelProperty(name = "tenantId", value = "租户ID")
    private String tenantId;
    /**
     * 微信unionId
     */
    @ApiModelProperty(name = "unionId", value = "微信unionId")
    private String unionId;
    /**
     *
     */
    @ApiModelProperty(name = "province", value = "省")
    private String province;
    /**
     * 城市
     */
    @ApiModelProperty(name = "city", value = "城市")
    private String city;
    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickName", value = "昵称")
    private String nickName;
    /**
     * 性别
     */
    @ApiModelProperty(name = "sex", value = "性别")
    private Integer sex;
    /**
     * 区域
     */
    @ApiModelProperty(name = "district", value = "区域")
    private String district;
    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;
    /**
     * 身份证
     */
    @ApiModelProperty(name = "identity", value = "身份证")
    private String identity;
    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码")
    private String password;
    /**
     * 电话
     */
    @ApiModelProperty(name = "tel", value = "电话")
    private String tel;
    /**
     * 头像
     */
    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;
    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;
    /**
     * 角色Id列表
     */
    @ApiModelProperty(name = "roleIdList", value = "角色Id列表")
    private List<String> roleIdList;
}