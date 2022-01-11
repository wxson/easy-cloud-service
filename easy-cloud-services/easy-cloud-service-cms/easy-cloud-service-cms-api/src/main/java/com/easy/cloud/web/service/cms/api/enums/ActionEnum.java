package com.easy.cloud.web.service.cms.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 活动枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum ActionEnum implements IBaseEnum {
    /**
     * 活动
     */
    DEFAULT(0, "无活动"),
    FIRST_PAY(1, "首冲"),
    SPECIAL_PROMOTION(2, "特价促销"),
    MONTH_CARD(3, "月卡"),
    SIGN_IN(4, "签到"),
    PROMOTION_88(5, "88元促销"),
    INVITE_FRIENDS(6, "邀请好友"),
    FREE_GIFT(7, "免费礼包"),
    HAPPY_BOUNCING(8, "欢乐对对碰"),
    VIP(9, "VIP"),
    CERTIFICATION(11, "实名认证"),
    BIND_PHONE(12, "绑定手机号"),
    DAILY_TASK(13, "每日任务"),
    DAILY_ACTIVE(14, "每日活跃"),
    DAILY_SUPPLY(15, "每日补给"),
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
    public static Optional<ActionEnum> getInstanceByCode(int code) {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (code == actionEnum.code) {
                return Optional.of(actionEnum);
            }
        }
        return Optional.empty();
    }
}
