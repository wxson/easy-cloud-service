package com.easy.cloud.web.service.member.biz.domain;

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
import java.math.BigDecimal;

/**
 * Member 持久类
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_member")
public class MemberDO extends BaseEntity {
    /**
     * 用户ID
     */
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
    private String userId;
    /**
     * 会员昵称
     */
    @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '会员昵称'")
    private String nickName;
    /**
     * VIP 等级
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT 'VIP 等级'")
    private Integer vipLevel;
    /**
     * 积分
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '积分'")
    private Integer points;
    /**
     * 经验值
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '经验值'")
    private Integer experience;
    /**
     * 总的充值
     */
    @Column(columnDefinition = "DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '总的充值'")
    private BigDecimal totalRecharge;
    /**
     * 个性签名
     */
    @Column(columnDefinition = "VARCHAR(255) DEFAULT '' COMMENT '个性签名'")
    private String profile;
}