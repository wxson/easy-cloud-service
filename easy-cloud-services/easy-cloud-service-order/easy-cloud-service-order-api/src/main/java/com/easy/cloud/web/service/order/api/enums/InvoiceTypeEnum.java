package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 发票类型
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum InvoiceTypeEnum implements IBaseEnum {
    /**
     * 1->企业发票；2->个人发票；
     */
    ENTERPRISE(1, "企业发票"),
    PERSONAL(2, "个人发票"),
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
    public static Optional<InvoiceTypeEnum> getInstanceByCode(int code) {
        for (InvoiceTypeEnum orderStatusEnum : InvoiceTypeEnum.values()) {
            if (code == orderStatusEnum.code) {
                return Optional.of(orderStatusEnum);
            }
        }
        return Optional.empty();
    }
}
