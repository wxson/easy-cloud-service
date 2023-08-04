package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Role 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_role")
public class RoleDO implements IConverter {

  /**
   * 文档ID，必须保证角色ID的全局唯一性
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * 租户ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '租户ID'")
  private String tenantId;
  /**
   * 角色编码
   */
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '角色编码'")
  private String code;
  /**
   * 角色名称
   */
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '描述'")
  private String name;
  /**
   * 描述
   */
  @Column(columnDefinition = "VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述'")
  private String describe;
  /**
   * 状态 0 启用 1 禁用
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'START_STATUS' COMMENT '状态'")
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'UN_DELETED' COMMENT '是否删除'")
  private DeletedEnum deleted;
  /**
   * 创建用户
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建用户'")
  private String createBy;
  /**
   * 创建时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建时间'")
  private String createAt;
  /**
   * 更新人员
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新人员'")
  private String updateBy;
  /**
   * 更新时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新时间'")
  private String updateAt;
}