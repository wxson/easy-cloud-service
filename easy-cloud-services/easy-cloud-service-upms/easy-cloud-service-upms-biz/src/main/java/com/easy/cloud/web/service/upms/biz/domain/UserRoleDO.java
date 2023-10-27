package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 用户权限关系表 一般用于支付相关权限功能
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_user_role")
public class UserRoleDO extends BaseEntity {

  /**
   * 用户ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
  private String userId;
  /**
   * 角色ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '角色ID'")
  private String roleId;

}
