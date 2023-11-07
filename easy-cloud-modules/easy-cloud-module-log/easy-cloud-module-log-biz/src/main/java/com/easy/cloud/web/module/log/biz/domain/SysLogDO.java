package com.easy.cloud.web.module.log.biz.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.module.log.api.dto.SysLogType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * OperationLog 持久类
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_sys_log")
public class SysLogDO extends BaseEntity {

  /**
   * 日志内容
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '日志内容'")
  private String name;
  /**
   * 日志内容
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '日志内容'")
  private String action;
  /**
   * 调用的方法名
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '调用的方法名'")
  private String methodName;
  /**
   * 调用的类名
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '调用的类名'")
  private String className;
  /**
   * 日志类型（NORMAL：正常日志，EXCEPTION：异常日志）
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(32) COMMENT '日志类型'")
  private SysLogType type;
  /**
   * 调用参数
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '调用参数'")
  private String params;
  /**
   * 请求地址
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '请求地址'")
  private String requestPath;
  /**
   * 远程地址远程地址
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '远程地址'")
  private String remoteAddr;
  /**
   * 耗时
   */
  @Column(columnDefinition = "INT COMMENT '远程地址'")
  private Integer elapsedTime;
  /**
   * 异常信息
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '异常信息'")
  private String exception;
}