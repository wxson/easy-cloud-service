package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
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
 * Department 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EnableTenant
@EnableLogic
@Table(name = "db_dept")
public class DeptDO extends BaseEntity {

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
  @Column(columnDefinition = "VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '父级部门ID'")
  private String parentId;
  /**
   * 负责人
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '负责人'")
  private String leader;
  /**
   * 联系电话
   */
  @Column(columnDefinition = "VARCHAR(11) COMMENT '联系电话'")
  private String tel;
  /**
   * 邮箱
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '邮箱'")
  private String email;
  /**
   * 描述
   */
  @Column(columnDefinition = "VARCHAR(225) COMMENT '描述'")
  private String remark;
}