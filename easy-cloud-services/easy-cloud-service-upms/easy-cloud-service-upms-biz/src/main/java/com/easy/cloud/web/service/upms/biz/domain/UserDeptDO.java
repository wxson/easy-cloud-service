package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 用户部门关系表
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
@Table(name = "db_user_dept")
public class UserDeptDO extends BaseEntity {

  /**
   * 用户ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
  private String userId;
  /**
   * 部门ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '部门ID'")
  private String deptId;

}
