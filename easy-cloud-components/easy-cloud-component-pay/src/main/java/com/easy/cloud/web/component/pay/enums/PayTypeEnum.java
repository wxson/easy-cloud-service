package com.easy.cloud.web.component.pay.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式枚举
 *
 * @author GR
 * @date 2021-11-12 14:47
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum implements IBaseEnum {
    /**
     * 支付方式
     */
    WX_PAY(1, "微信支付"),
    ALI_PAY(2, "阿里支付"),
    VIRTUAL_PAY(9, "虚拟货币支付"),
    ;
    private final int code;
    private final String desc;

}
