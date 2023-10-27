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
 * 角色权限关系表
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
@Table(name = "db_role_menu")
public class RoleMenuDO extends BaseEntity {

  /**
   * 角色ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '角色编码'")
  private String roleId;
  /**
   * 菜单ID
   */
  @Column(columnDefinition = "LONG NOT NULL COMMENT '菜单ID'")
  private String menuId;
}
