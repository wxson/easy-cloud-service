package com.easy.cloud.web.module.certification.api.dto;

import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * PersonalAuthentication请求数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PersonalAuthenticationDTO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 用户ID
     */
    @NotBlank(message = "认证用户ID不能为空")
    private String userId;
    /**
     * 用户名称
     */
    @NotBlank(message = "认证用户名不能为空")
    private String userName;
    /**
     * 用户身份证
     */
    @NotBlank(message = "认证用户身份证不能为空")
    private String identityCard;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 联系邮件
     */
    private String email;
    /**
     * 已认证
     */
    private Boolean authenticated;
}