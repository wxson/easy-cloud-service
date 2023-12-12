package com.easy.cloud.web.module.sms.biz.domain;

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
 * Sms 持久类
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_sms")
public class SmsDO extends BaseEntity {

  /**
   * 短信验证码
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '短信验证码'")
  private String code;
  /**
   * 电话
   */
  @Column(columnDefinition = "VARCHAR(11) NOT NULL COMMENT '电话'")
  private String tel;
  /**
   * 短信内容
   */
  @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '短信内容'")
  private String content;
}