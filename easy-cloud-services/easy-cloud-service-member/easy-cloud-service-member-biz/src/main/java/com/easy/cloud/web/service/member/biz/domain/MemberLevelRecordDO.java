package com.easy.cloud.web.service.member.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MemberLevelRecord 持久类
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_member_level_record")
public class MemberLevelRecordDO extends BaseEntity {
    /**
     * 用户
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
    private String userId;
    /**
     * 上一等级ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '上一等级ID'")
    private String lastLevelId;
    /**
     * 上一等级名称
     */
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '上一等级名称'")
    private Integer lastLevelName;
    /**
     * 上一等级
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '上一等级'")
    private Integer lastLevel;
    /**
     * 当前等级ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '当前等级ID'")
    private String currentLevelId;
    /**
     * 当前等级名称
     */
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '当前等级名称'")
    private Integer currentLevelName;
    /**
     * 当前等级
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '当前等级'")
    private Integer currentLevel;
    /**
     * 当前经验值
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '当前经验值'")
    private Integer currentExperience;
    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
}