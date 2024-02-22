package com.easy.cloud.web.module.certification.api.vo;

import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * UserAuthentication展示数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationVO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 用户ID/账号ID
     */
    private String userId;
    /**
     * 认证ID
     */
    private String authenticationId;
    /**
     * 认证状态
     */
    private AuthenticationTypeEnum authenticationType;
    /**
     * 认证状态
     */
    private AuthenticationStatusEnum authenticationStatus;
    /**
     * 审核备注
     */
    private String remark;
    /**
     * 已认证
     */
    private Boolean authenticated;
}