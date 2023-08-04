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
 * Department 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_department")
public class DepartmentDO implements IConverter {

  /**
   * 文档ID
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
   * 部门名称
   */
  @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '部门名称'")
  private String name;
  /**
   * 排序
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '排序'")
  private Integer sort;
  /**
   * 上级部门
   */
  @Column(columnDefinition = "LONG NOT NULL DEFAULT '0' COMMENT '父级部门ID'")
  private Long parentId;
  /**
   * 负责人
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '负责人'")
  private String leader;
  /**
   * 联系电话
   */
  @Column(columnDefinition = "VARCHAR(11) COMMENT '联系电话'")
  private String phone;
  /**
   * 邮箱
   */
  @Column(columnDefinition = "VARCHAR(11) COMMENT '联系电话'")
  private String email;
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