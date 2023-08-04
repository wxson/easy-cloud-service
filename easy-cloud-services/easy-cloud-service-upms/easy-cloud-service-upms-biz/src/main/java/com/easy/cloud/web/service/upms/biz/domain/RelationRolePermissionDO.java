package com.easy.cloud.web.service.upms.biz.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色权限关系表
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_relation_role_permission")
public class RelationRolePermissionDO implements Serializable {

  /**
   * ID
   */
  @Id
  private Long id;
  /**
   * 角色ID
   */
  @Column(columnDefinition = "LONG NOT NULL COMMENT '角色ID'")
  private Long roleId;
  /**
   * 菜单ID
   */
  @Column(columnDefinition = "LONG NOT NULL COMMENT '菜单ID'")
  private Long menuId;
  /**
   * 创建时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建时间'")
  private String createAt;
  /**
   * 更新时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新时间'")
  private String updateAt;
}
