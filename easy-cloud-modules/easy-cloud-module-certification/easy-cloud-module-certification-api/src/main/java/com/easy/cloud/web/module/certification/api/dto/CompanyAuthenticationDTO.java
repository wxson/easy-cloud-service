package com.easy.cloud.web.module.certification.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * CompanyAuthentication请求数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAuthenticationDTO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 用户ID/账号ID
     */
    @NotBlank(message = "认证用户ID不能为空")
    private String userId;
    /**
     * 公司名称
     */
    @NotBlank(message = "企业名称不能为空")
    private String companyName;
    /**
     * 公司地址
     */
    @NotBlank(message = "企业地址不能为空")
    private String companyAddress;
    /**
     * 公司许可证文件地址
     */
    @NotBlank(message = "公司许可证文件地址不能为空")
    private String companyLicense;
    /**
     * 法人
     */
    @NotBlank(message = "法人不能为空")
    private String legalPerson;
    /**
     * 法人身份证
     */
    @NotBlank(message = "法人身份证不能为空")
    private String legalPersonIdentityCard;
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String tel;
    /**
     * 联系邮件
     */
    private String email;
}