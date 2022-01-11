package com.easy.cloud.web.service.upms.biz.enums;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import com.easy.cloud.web.service.upms.biz.social.impl.AppleSocialServiceImpl;
import com.easy.cloud.web.service.upms.biz.social.impl.QqSocialServiceImpl;
import com.easy.cloud.web.service.upms.biz.social.impl.WxSocialServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 登录方式类型枚举
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
public enum SocialTypeEnum {
    /**
     * 角色枚举类型
     */
    // 使用ShareSDK授权登录，微信、QQ使用同一套登录逻辑
    WX(1, "wx", "微信登录", WxSocialServiceImpl.class),
    QQ(2, "qq", "QQ登录", QqSocialServiceImpl.class),
    APPLE(3, "apple", "苹果登录", AppleSocialServiceImpl.class),
    ;

    private final int code;
    private final String prefix;
    private final String describe;
    private final Class<?> implClass;

    /**
     * 获取用户行为实现类
     *
     * @return java.util.Optional<com.easy.cloud.web.service.mj.biz.action.ISocialService>
     */
    public static Optional<ISocialService> getSocialService(String type) {
        for (SocialTypeEnum socialTypeEnum : SocialTypeEnum.values()) {
            if (socialTypeEnum.prefix.equals(type)) {
                String simpleName = socialTypeEnum.getImplClass().getSimpleName();
                return Optional.of(SpringContextHolder.getBean(StrUtil.lowerFirst(simpleName), ISocialService.class));
            }
        }
        return Optional.empty();
    }

    /**
     * 根据类型获取枚举类
     *
     * @param type 登录类型
     * @return java.util.Optional<com.easy.cloud.web.service.upms.biz.enums.SocialTypeEnum>
     */
    public static Optional<SocialTypeEnum> getSocialByType(String type) {
        for (SocialTypeEnum socialTypeEnum : SocialTypeEnum.values()) {
            if (socialTypeEnum.prefix.equals(type)) {
                return Optional.of(socialTypeEnum);
            }
        }
        return Optional.empty();
    }
}
