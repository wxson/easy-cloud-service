package com.easy.cloud.web.module.certification.api.vo;

import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * PersonalAuthentication展示数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PersonalAuthenticationVO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户身份证
     */
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
     * 认证状态
     */
    private AuthenticationStatusEnum status;
    /**
     * 审核备注
     */
    private String remark;
    /**
     * 已认证
     */
    private Boolean authenticated;
}