package com.easy.cloud.web.service.upms.api.vo;

import com.easy.cloud.web.service.upms.api.enums.OperationLogType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * OperationLog展示数据
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysLogVO {

  /**
   * 文档ID
   */
  private String id;
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
  private Date createAt;
}