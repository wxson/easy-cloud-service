package com.easy.cloud.web.service.member.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * VIP等级
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum VipLevelEnum implements IBaseEnum {
    /**
     * VIP等级
     */
    VIP_1(1, "VIP 1"),
    VIP_2(2, "VIP 2"),
    VIP_3(3, "VIP 3"),
    VIP_4(4, "VIP 4"),
    VIP_5(5, "VIP 5"),
    VIP_6(6, "VIP 6"),
    VIP_7(7, "VIP 7"),
    VIP_8(8, "VIP 8"),
    VIP_9(9, "VIP 9"),
    VIP_10(10, "VIP 10"),
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
    public static Optional<VipLevelEnum> getInstanceByCode(int code) {
        for (VipLevelEnum goodsTypeEnum : VipLevelEnum.values()) {
            if (code == goodsTypeEnum.code) {
                return Optional.of(goodsTypeEnum);
            }
        }
        return Optional.empty();
    }
}
