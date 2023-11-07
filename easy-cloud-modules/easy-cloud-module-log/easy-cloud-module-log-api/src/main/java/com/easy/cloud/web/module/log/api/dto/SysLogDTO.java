package com.easy.cloud.web.module.log.api.dto;

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
public class SysLogDTO {

  /**
   * 日志内容
   */
  private String name;
  /**
   * 操作类型
   */
  private String action;
  /**
   * 执行方法
   */
  private String method;
  /**
   * 用户代理
   */
  private String userAgent;
  /**
   * 日志类型
   */
  private SysLogType type;
  /**
   * 参数
   */
  private String params;
  /**
   * 请求地址
   */
  private String requestPath;
  /**
   * 服务地址
   */
  private String remoteAddr;
  /**
   * 消耗时间
   */
  private Integer elapsedTime;
  /**
   * 异常信息
   */
  private String exception;
}