package com.easy.cloud.web.service.member.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
   * VIP 等级
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT 'VIP 等级'")
  private Integer vipLevel;
  /**
   * 金额
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '金额/金币'")
  private Integer amount;
  /**
   * 钻石
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '钻石'")
  private Integer diamond;
  /**
   * 是否删除
   */
  @Column(columnDefinition = "tinyint NOT NULL DEFAULT '0' COMMENT '是否删除'")
  private Integer deleted;
  /**
   * 点券
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '点券'")
  private Integer coupon;
  /**
   * 等级
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '等级'")
  private Integer level;
  /**
   * 形象IP=英雄ID
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '形象IP=英雄ID'")
  private Integer imageIp;
  /**
   * 经验值
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '经验值'")
  private Integer experience;
  /**
   * 用户ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
  private String userId;
  /**
   * 总的充值
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '总的充值'")
  private Integer totalRecharge;
  /**
   * 个性签名
   */
  @Column(columnDefinition = "VARCHAR(255) DEFAULT '' COMMENT '个性签名'")
  private String profile;
}