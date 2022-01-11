package com.easy.cloud.web.service.order.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 支付状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum implements IBaseEnum {
    /**
     * 新订单（PAY_NO），部分支付（PAY_PARTIAL），已付款（PAY_YES）
     */
    PAY_NO(1, "新订单，待支付"),
    PAY_PARTIAL(2, "部分支付"),
    PAY_YES(3, "已付款"),
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
     * 根据code获取牌型对象
     *
     * @param code 编码
     * @return java.util.Optional<com.easy.cloud.web.service.mj.biz.enums.HuCardRuleEnum>
     */
    public static Optional<PayStatusEnum> getInstanceByCode(int code) {
        for (PayStatusEnum orderStatusEnum : PayStatusEnum.values()) {
            if (code == orderStatusEnum.code) {
                return Optional.of(orderStatusEnum);
            }
        }
        return Optional.empty();
    }
}
