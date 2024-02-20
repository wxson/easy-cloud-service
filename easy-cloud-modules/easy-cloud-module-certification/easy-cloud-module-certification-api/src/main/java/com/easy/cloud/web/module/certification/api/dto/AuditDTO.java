package com.easy.cloud.web.module.certification.api.dto;

import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 审核请求数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 审核状态
     */
    private AuthenticationStatusEnum status;
    /**
     * 备注
     */
    private String remark;
}