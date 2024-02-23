package com.easy.cloud.web.module.certification.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * CompanyAuthentication 持久类
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_company_authentication")
public class CompanyAuthenticationDO extends BaseEntity {
    /**
     * 用户ID/账号ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID/账号ID'")
    private String userId;
    /**
     * 公司名称
     */
    @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '公司名称'")
    private String companyName;
    /**
     * 统一社会信用代码：Unified Social Credit Identifier
     */
    @Column(columnDefinition = "VARCHAR(18) NOT NULL COMMENT '统一社会信用代码'")
    private String usci;
    /**
     * 公司地址
     */
    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '公司地址'")
    private String companyAddress;
    /**
     * 公司许可证文件地址
     */
    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '公司许可证文件地址'")
    private String companyLicense;
    /**
     * 法人
     */
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '法人'")
    private String legalPerson;
    /**
     * 法人身份证
     */
    @Column(columnDefinition = "VARCHAR(18) NOT NULL COMMENT '法人身份证'")
    private String legalPersonIdentityCard;
    /**
     * 联系电话
     */
    @Column(columnDefinition = "VARCHAR(11) NOT NULL COMMENT '联系电话'")
    private String tel;
    /**
     * 联系邮件
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '联系邮件'")
    private String email;
    /**
     * 认证状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL  DEFAULT 'WAIT' COMMENT '认证状态'")
    private AuthenticationStatusEnum authenticationStatus;
    /**
     * 审核备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '审核备注'")
    private String remark;
    /**
     * 已认证
     */
    @Column(columnDefinition = "TINYINT(1) NOT NULL DEFAULT '0' COMMENT '已认证'")
    private Boolean authenticated;
}