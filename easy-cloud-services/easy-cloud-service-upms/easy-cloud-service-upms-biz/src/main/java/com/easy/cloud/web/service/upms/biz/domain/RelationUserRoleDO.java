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
 * 用户权限关系表 一般用于支付相关权限功能
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_relation_user_role")
public class RelationUserRoleDO implements Serializable {

  /**
   * ID
   */
  @Id
  private Long id;
  /**
   * 用户ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '用户ID'")
  private String userId;
  /**
   * 角色ID
   */
  @Column(columnDefinition = "LONG NOT NULL COMMENT '角色ID'")
  private Long roleId;
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
