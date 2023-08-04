package com.easy.cloud.web.module.dict.domain.db;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.SysFlagEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Dict 持久类
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_dict")
public class DictDO implements IConverter {

  /**
   * 文档ID
   */
  @Id
  private Long id;

  /**
   * 租户
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '租户ID'")
  private String tenantId;
  /**
   *
   */
  private String name;
  /**
   *
   */
  private String remark;
  /**
   *
   */
  private String describe;
  /**
   *
   */
  private String type;
  /**
   * 系统标识 0 系统数据 1 其他
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'START_STATUS' COMMENT '状态'")
  private SysFlagEnum sysFlag;
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