package com.easy.cloud.web.module.certification.api.vo;

import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * AuthenticationRecord展示数据
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRecordVO {

    /**
     * 文档ID
     */
    private String id;
    /**
     * 认证的文档ID
     */
    private String authenticationId;
    /**
     * 认证类型
     */
    private AuthenticationTypeEnum type;
    /**
     * 认证状态
     */
    private AuthenticationStatusEnum status;
    /**
     * 审核备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 创建人
     */
    private String createBy;
}