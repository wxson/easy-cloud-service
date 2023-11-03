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
 * DictItem 持久类
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_dict_item")
public class DictItemDO extends BaseEntity {

  /**
   * 字典类型
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '字典类型'")
  private String dictType;
  /**
   * 字典标签
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '字典标签'")
  private String label;
  /**
   * 字典值
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '字典值'")
  private String value;
  /**
   * 字典说明
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '字典说明'")
  private String remark;
}