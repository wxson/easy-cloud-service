package com.easy.cloud.web.module.log.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.upms.api.enums.OperationLogType;
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
}