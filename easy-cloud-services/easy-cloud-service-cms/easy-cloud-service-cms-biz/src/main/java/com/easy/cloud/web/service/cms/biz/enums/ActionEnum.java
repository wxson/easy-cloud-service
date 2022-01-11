package com.easy.cloud.web.service.cms.biz.enums;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.enums.IBaseEnum;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.service.cms.biz.service.IActionService;
import com.easy.cloud.web.service.cms.biz.service.action.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
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
    DEFAULT(0, "无活动", null),
    FIRST_PAY(1, "首冲", FirstPayActionServiceImpl.class),
    SPECIAL_PROMOTION(2, "特价促销", SpecialPromotionActionServiceImpl.class),
    MONTH_CARD(3, "月卡", MonthCardActionServiceImpl.class),
    SIGN_IN(4, "签到", SignInActionServiceImpl.class),
    PROMOTION_88(5, "88元促销", Promotion88ActionServiceImpl.class),
    INVITE_FRIENDS(6, "邀请好友", InviteFriendsActionServiceImpl.class),
    FREE_GIFT(7, "免费礼包", FreeGiftActionServiceImpl.class),
    HAPPY_BOUNCING(8, "欢乐对对碰", HappyBouncingActionServiceImpl.class),
    VIP(9, "VIP", VipActionServiceImpl.class),
    CERTIFICATION(11, "实名认证", CertificationActionServiceImpl.class),
    BIND_PHONE(12, "绑定手机号", BindPhoneActionServiceImpl.class),
    DAILY_TASK(13, "每日任务", DailyTaskActionServiceImpl.class),
    DAILY_ACTIVE(14, "每日活跃", DailyActiveActionServiceImpl.class),
    DAILY_SUPPLY(15, "每日补给", DailySupplyActionServiceImpl.class),
    BIND_FRIENDS(16, "被邀请好友", BindFriendsActionServiceImpl.class),
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
     * 实现类
     */
    private final Class<?> implClass;

    /**
     * 获取实现类
     *
     * @return java.util.Optional<com.easy.cloud.web.service.pay.biz.service.IPayService>
     */
    public Optional<IActionService> getActionService() {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (actionEnum.getCode() == code) {
                if (Objects.isNull(actionEnum.getImplClass())) {
                    return Optional.empty();
                }
                String simpleName = actionEnum.getImplClass().getSimpleName();
                return Optional.of(SpringContextHolder.getBean(StrUtil.lowerFirst(simpleName), IActionService.class));
            }
        }
        return Optional.empty();
    }

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
