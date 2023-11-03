package com.easy.cloud.web.module.dict.biz.domain;

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
 * Dict 持久类
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_dict")
public class DictDO extends BaseEntity {

  /**
   * 字典名称
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '字典名称'")
  private String name;
  /**
   * 字典类型
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '字典类型'")
  private String type;
  /**
   * 字典说明
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '字典说明'")
  private String remark;
}