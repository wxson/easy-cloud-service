package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货币类型
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum CurrencyTypeEnum implements IBaseEnum {
  /**
   * 货币
   */
  CNY(1, "人民币"),
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
  public static Optional<CurrencyTypeEnum> getInstanceByCode(int code) {
    for (CurrencyTypeEnum currencyTypeEnum : CurrencyTypeEnum.values()) {
      if (code == currencyTypeEnum.code) {
        return Optional.of(currencyTypeEnum);
      }
    }
    return Optional.empty();
  }
}
