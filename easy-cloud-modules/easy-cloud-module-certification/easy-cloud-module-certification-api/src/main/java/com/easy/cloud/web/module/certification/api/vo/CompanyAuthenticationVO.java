package com.easy.cloud.web.module.certification.api.vo;

import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * CompanyAuthentication展示数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAuthenticationVO {
    /**
     * 文档ID
     */
    private String id;

    /**
     * 用户ID/账号ID
     */
    private String userId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司许可证文件地址
     */
    private String companyLicense;
    /**
     * 法人
     */
    private String legalPerson;
    /**
     * 法人身份证
     */
    private String legalPersonIdentityCard;
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