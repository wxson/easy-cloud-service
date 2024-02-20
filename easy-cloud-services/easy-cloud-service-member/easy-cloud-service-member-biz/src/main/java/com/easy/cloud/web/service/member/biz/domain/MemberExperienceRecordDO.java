package com.easy.cloud.web.service.member.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.member.api.enums.ExperienceBizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * MemberExperienceRecord 持久类
 *
 * @author Fast Java
 * @date 2024-02-18 16:52:27
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_member_experience_record")
public class MemberExperienceRecordDO extends BaseEntity {
    /**
     * 用户
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
    private String userId;
    /**
     * 上一经验值
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '上一经验值'")
    private Integer lastExperience;
    /**
     * 当前经验值
     */
    @Column(columnDefinition = "INT NOT NULL COMMENT '当前经验值'")
    private Integer currentExperience;
    /**
     * 经验值业务类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '经验值业务类型'")
    private ExperienceBizTypeEnum experienceBizType;
    /**
     * 经验增加值：正数为增加，负数为扣减
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '经验增加值：正数为增加，负数为扣减'")
    private Integer offsetExperience;
    /**
     * 备注
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
}