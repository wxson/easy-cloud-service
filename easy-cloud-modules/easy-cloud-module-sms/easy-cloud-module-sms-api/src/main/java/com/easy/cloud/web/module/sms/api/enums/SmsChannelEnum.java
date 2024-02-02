package com.easy.cloud.web.module.sms.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 短信渠道枚举
 *
 * @author GR
 * @since 2021/1/25 10:56
 */
@Getter
@AllArgsConstructor
public enum SmsChannelEnum implements IBaseEnum {
    /**
     * 短信渠道
     */
    ALI_YUN(1, "阿里云"),
    DING_TALK(2, "钉钉"),
    TENCENT(3, "腾讯云"),
    HUA_WEI(4, "华为云"),
    ;
    private final int code;
    private final String name;

    /**
     * 根据枚举code获取枚举对象
     *
     * @param code 枚举编码
     * @return java.util.Optional<com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum>
     */
    public static Optional<SmsChannelEnum> getInstanceByCode(int code) {
        for (SmsChannelEnum value : SmsChannelEnum.values()) {
            if (value.code == code) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

}
