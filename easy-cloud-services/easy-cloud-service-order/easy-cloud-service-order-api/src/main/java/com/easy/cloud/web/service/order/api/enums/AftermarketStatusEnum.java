package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 售后状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum AftermarketStatusEnum implements IBaseEnum {
  /**
   * 未申请（NOT_APPLY），已申请（APPLY），已失效（EXPIRED）。
   */
  NOT_APPLY(1, "未申请"),
  APPLY(2, "已申请"),
  EXPIRED(3, "已失效"),
  ;
  /**
   * 编码
   */
  private final int code;
  /**
   * 描述
   */
  private final String desc;

  /**
   * 根据code获取对象
   *
   * @param code 编码
   * @return java.util.Optional<com.easy.cloud.web.service.mj.biz.enums.HuCardRuleEnum>
   */
  public static Optional<AftermarketStatusEnum> getInstanceByCode(int code) {
    for (AftermarketStatusEnum orderStatusEnum : AftermarketStatusEnum.values()) {
      if (code == orderStatusEnum.code) {
        return Optional.of(orderStatusEnum);
      }
    }
    return Optional.empty();
  }
}
