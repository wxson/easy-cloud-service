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
 * Role 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_role")
public class RoleDO extends BaseEntity {

  /**
   * 角色编码
   */
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '角色编码'")
  private String code;
  /**
   * 角色名称
   */
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '角色名称'")
  private String name;
  /**
   * 描述
   */
  @Column(columnDefinition = "VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述'")
  private String remark;
  /**
   * 排序字段，数值越小越排靠前
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '排序'")
  private Integer sort;
}