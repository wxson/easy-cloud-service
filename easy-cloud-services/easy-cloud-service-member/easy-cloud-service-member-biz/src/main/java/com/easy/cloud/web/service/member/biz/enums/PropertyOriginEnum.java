package com.easy.cloud.web.service.member.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 资产来源
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum PropertyOriginEnum implements IBaseEnum {
    /**
     * 资产来源
     */
    BUY(1, "购买"),
    PAY(2, "支付"),
    PLAY(3, "对局"),
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
    public static Optional<PropertyOriginEnum> getInstanceByCode(int code) {
        for (PropertyOriginEnum goodsTypeEnum : PropertyOriginEnum.values()) {
            if (code == goodsTypeEnum.code) {
                return Optional.of(goodsTypeEnum);
            }
        }
        return Optional.empty();
    }
}
