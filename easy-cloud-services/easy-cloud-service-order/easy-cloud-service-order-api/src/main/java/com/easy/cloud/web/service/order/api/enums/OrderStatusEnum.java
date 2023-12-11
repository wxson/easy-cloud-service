package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements IBaseEnum {
  /**
   * 新订单（NEW），出库失败（OUT_DB_ERROR），已确认（CONFIRM），已付款（PAID_OFF）,已发货（SHIPPED），已收货（ROG），已完成（COMPLETE），已取消（CANCELLED），售后中（AFTER_SERVICE）
   */
  NEW(1, "新订单"),
  OUT_DB_ERROR(2, "出库失败"),
  CONFIRM(3, "已确认"),
  PAID_OFF(4, "已付款"),
  SHIPPED(5, "已发货"),
  ROG(6, "已收货"),
  COMPLETE(7, "已完成"),
  CANCELLED(8, "已取消"),
  AFTER_SERVICE(9, "售后中"),
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
  public static Optional<OrderStatusEnum> getInstanceByCode(int code) {
    for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
      if (code == orderStatusEnum.code) {
        return Optional.of(orderStatusEnum);
      }
    }
    return Optional.empty();
  }
}
