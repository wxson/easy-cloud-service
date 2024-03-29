package com.easy.cloud.web.component.mysql.domain;

import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.component.mysql.listener.EntityListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author GR
 * @date 2023/10/24 16:29
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class BaseEntity implements IConverter, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @Id
    @GenericGenerator(
            name = "easyCloudGenerator",
            strategy = "com.easy.cloud.web.component.mysql.utils.IdGeneratorUtil"
    )
    @GeneratedValue(generator = "easyCloudGenerator")
    protected String id;

    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身 一个企业、一个单位或一所学校只能有一个租户
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL DEFAULT '1' COMMENT '租户ID'")
    protected String tenantId;


    /**
     * 状态 0 启用 1 禁用
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL  DEFAULT 'START_STATUS' COMMENT '状态'")
    protected StatusEnum status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL  DEFAULT 'UN_DELETED' COMMENT '是否删除'")
    protected DeletedEnum deleted;

    /**
     * 创建人
     */
//  @CreatedBy
    @Column(columnDefinition = "VARCHAR(64) DEFAULT NULL COMMENT '创建人'")
    protected String createBy;

    /**
     * 创建时间
     */
//  @CreatedDate
    @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
    @DateTimeFormat(pattern = DateTimeConstants.DEFAULT_FORMAT)
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '创建时间'")
    protected Date createAt;

    /**
     * 更新用户
     */
//  @LastModifiedBy
    @Column(columnDefinition = "VARCHAR(64) DEFAULT NULL COMMENT '更新用户'")
    protected String updateBy;

    /**
     * 更新时间
     */
//  @LastModifiedDate
    @JsonFormat(pattern = DateTimeConstants.DEFAULT_FORMAT, timezone = DateTimeConstants.DEFAULT_TIMEZONE)
    @DateTimeFormat(pattern = DateTimeConstants.DEFAULT_FORMAT)
    @Column(columnDefinition = "VARCHAR(32) DEFAULT NULL COMMENT '更新时间'")
    protected Date updateAt;
}
