package com.easy.cloud.web.module.certification.biz.service.certification.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 认证信息
 *
 * @author GR
 * @date 2024/2/21 13:54
 */
@Data
@Builder
@Accessors(chain = true)
public class CertificationBody {

    /**
     * 认证用户姓名
     */
    private String userName;

    /**
     * 认证用户身份证
     */
    private String idCard;

    /**
     * 认证用户姓名
     */
    private String tel;

    /**
     * 统一社会信用代码：Unified Social Credit Identifier
     */
    private String usci;
}
