package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 物流状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum LogisticsStatusEnum implements IBaseEnum {
  /**
   * 未发货（SHIP_NO），已发货（SHIP_YES），已收货（SHIP_ROG）
   */
  SHIP_NO(1, "未发货"),
  SHIP_YES(2, "已发货"),
  SHIP_ROG(3, "已收货"),
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
  public static Optional<LogisticsStatusEnum> getInstanceByCode(int code) {
    for (LogisticsStatusEnum orderStatusEnum : LogisticsStatusEnum.values()) {
      if (code == orderStatusEnum.code) {
        return Optional.of(orderStatusEnum);
      }
    }
    return Optional.empty();
  }
}
