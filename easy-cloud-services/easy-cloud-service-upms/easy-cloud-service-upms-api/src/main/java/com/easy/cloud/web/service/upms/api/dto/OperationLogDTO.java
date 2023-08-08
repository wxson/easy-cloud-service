package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.service.upms.api.enums.OperationLogType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogDTO {

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
  private String method;
  /**
   * 用户代理
   */
  private String userAgent;
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