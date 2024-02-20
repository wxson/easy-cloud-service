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
 * PersonalAuthentication 持久类
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_personal_authentication")
public class PersonalAuthenticationDO extends BaseEntity {
    /**
     * 用户ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
    private String userId;
    /**
     * 用户名称
     */
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '用户名称'")
    private String userName;
    /**
     * 用户身份证
     */
    @Column(columnDefinition = "VARCHAR(18) NOT NULL COMMENT '用户身份证'")
    private String identityCard;
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
    private AuthenticationStatusEnum status;
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