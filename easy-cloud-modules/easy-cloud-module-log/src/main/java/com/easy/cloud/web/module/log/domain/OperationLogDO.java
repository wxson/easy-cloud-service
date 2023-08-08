package com.easy.cloud.web.module.log.domain;

import com.easy.cloud.web.service.upms.api.enums.OperationLogType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OperationLog 持久类
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_operationLog")
public class OperationLogDO {

  /**
   * 文档ID
   */
  @Id
  private Long id;
  /**
   *
   */
  private String name;
  /**
   *
   */
  private String action;
  /**
   *
   */
  private String methodName;
  /**
   *
   */
  private String className;
  /**
   *
   */
  private OperationLogType type;
  /**
   *
   */
  private String params;
  /**
   *
   */
  private String requestUri;
  /**
   *
   */
  private String serviceId;
  /**
   *
   */
  private Long elapsedTime;
  /**
   *
   */
  private String exception;
  /**
   * 创建用户
   */
  private String createBy;
  /**
   * 创建时间
   */
  private String createAt;
}