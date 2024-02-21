package com.easy.cloud.web.module.certification.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * UserAuthenticationDO 持久类
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
@Table(name = "db_user_authentication")
public class UserAuthenticationDO extends BaseEntity {
    /**
     * 用户ID/账号ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID/账号ID'")
    private String userId;
    /**
     * 认证ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '认证ID'")
    private String authenticationId;
    /**
     * 认证状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '认证类型'")
    private AuthenticationTypeEnum authenticationType;
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