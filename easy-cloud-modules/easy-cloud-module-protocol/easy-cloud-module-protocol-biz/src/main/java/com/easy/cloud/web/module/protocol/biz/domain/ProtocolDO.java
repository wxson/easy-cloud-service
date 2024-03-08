package com.easy.cloud.web.module.protocol.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Protocol 持久类
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_protocol")
public class ProtocolDO extends BaseEntity {

    /**
     * 应用唯一标识：可手动设置
     */
    @Column(columnDefinition = "VARCHAR(64) COMMENT '应用唯一标识：可手动设置'")
    private String uniqueId;

    /**
     * 协议名称
     */
    @Column(columnDefinition = "VARCHAR(125) COMMENT '协议名称'")
    private String name;
    /**
     * 协议内容
     */
    @Column(columnDefinition = "TEXT COMMENT '协议内容'")
    private String content;
    /**
     * 描述
     */
    @Column(columnDefinition = "VARCHAR(125) COMMENT '描述'")
    private String description;
    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(125) COMMENT '备注'")
    private String remark;
}