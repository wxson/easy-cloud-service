package com.easy.cloud.web.component.core.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Status枚举：0：启用状态 1：禁用状态
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "", description = "状态枚举：0：启用状态 1：禁用状态 2：冻结状态")
public enum StatusEnum implements IBaseEnum {
  /**
   * Status枚举：0：启用状态 1：禁用状态 2：冻结状态
   */
  FORBID_STATUS(0, "禁用"),
  START_STATUS(1, "启用"),
  FREEZE_STATUS(2, "冻结"),
  ;

  private final int code;
  private final String desc;
}
