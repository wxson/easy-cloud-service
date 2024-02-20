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
 * AuthenticationRecord 持久类
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_authentication_record")
public class AuthenticationRecordDO extends BaseEntity {
    /**
     * 认证的文档ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '审核备注'")
    private String authenticationId;
    /**
     * 认证类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '认证类型'")
    private AuthenticationTypeEnum type;
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
}