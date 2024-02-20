package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 订单来源枚举
 *
 * @author GR
 * @date 2024/2/6 11:08
 */
@Getter
@AllArgsConstructor
public enum SourceTypeEnum implements IBaseEnum {
    /**
     * 订单来源：1：APP；2：PC；3：小程序
     */
    APP(1, "APP端"),
    PC(2, "PC端"),
    WX(3, "微信小程序"),
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
    public static Optional<SourceTypeEnum> getInstanceByCode(int code) {
        for (SourceTypeEnum sourceTypeEnum : SourceTypeEnum.values()) {
            if (code == sourceTypeEnum.code) {
                return Optional.of(sourceTypeEnum);
            }
        }
        return Optional.empty();
    }
}
