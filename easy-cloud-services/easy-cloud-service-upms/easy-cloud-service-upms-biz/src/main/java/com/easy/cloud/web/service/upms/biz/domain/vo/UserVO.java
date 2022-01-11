package com.easy.cloud.web.service.upms.biz.domain.vo;

import com.easy.cloud.web.component.core.service.IConvertProxy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Fast Java
 * @date 2021-03-16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "用户数据", description = "用户返回数据")
public class UserVO implements IConvertProxy {
    /**
     * 文档ID
     */
    @ApiModelProperty(name = "id", value = "文档ID")
    private String id;
    /**
     * 账号
     */
    @ApiModelProperty(name = "account", value = "账号")
    private String account;
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
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;
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