package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author GR
 * @date 2023/8/8 12:07
 */
@Getter
@AllArgsConstructor
public enum OperationLogType {
  /**
   * 操作日志类型
   */
  NORMAL(0, "正常日志"),
  EXCEPTION(1, "异常日志"),
  ;

  private final int code;
  private final String desc;
}
