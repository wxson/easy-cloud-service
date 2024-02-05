package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 订单类型
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum implements IBaseEnum {
    /**
     * 1->商品订单；2->转账订单；3->充值订单
     */
    GOODS(1, "商品订单"),
    TRANSFER(2, "转账订单"),
    RECHARGE(3, "充值订单"),
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
    public static Optional<OrderTypeEnum> getInstanceByCode(int code) {
        for (OrderTypeEnum orderStatusEnum : OrderTypeEnum.values()) {
            if (code == orderStatusEnum.code) {
                return Optional.of(orderStatusEnum);
            }
        }
        return Optional.empty();
    }
}
