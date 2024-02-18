package com.easy.cloud.web.service.member.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MemberLevel 持久类
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_member_level")
public class MemberLevelDO extends BaseEntity {

    /**
     * 会会员等级
     */
    private Integer level;
    /**
     * 会员名称
     */
    private String name;
    /**
     * 会员经验边界，即超出该经验值自动升级
     */
    private Integer limitExperience;
}