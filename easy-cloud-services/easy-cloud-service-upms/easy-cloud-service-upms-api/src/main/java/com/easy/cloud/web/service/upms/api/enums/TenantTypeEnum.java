package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租户类型枚举
 *
 * @author GR
 * @date 2020-9-22 16:44
 */
@Getter
@AllArgsConstructor
public enum TenantTypeEnum {
  /**
   * 未知的性别
   */
  ENTERPRISE(0, "企业"),
  /**
   * 男性
   */
  PERSONAL(1, "个人"),
  ;

  private final int code;
  private final String desc;

  public static TenantTypeEnum getInstance(int gender) {
    for (TenantTypeEnum tenantTypeEnum : TenantTypeEnum.values()) {
      if (tenantTypeEnum.getCode() == gender) {
        return tenantTypeEnum;
      }
    }
    return null;
  }
}
