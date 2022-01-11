package com.easy.cloud.web.service.cms.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 活动奖励方式
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum ActionRewardWayEnum implements IBaseEnum {
    /**
     * 商品类型
     */
    SINGLE(1, "一次性"),
    FIXED_CYCLE(2, "固定周期"),
    PERMANENT(3, "永久"),
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
    public static Optional<ActionRewardWayEnum> getInstanceByCode(int code) {
        for (ActionRewardWayEnum goodsTypeEnum : ActionRewardWayEnum.values()) {
            if (code == goodsTypeEnum.code) {
                return Optional.of(goodsTypeEnum);
            }
        }
        return Optional.empty();
    }
}
