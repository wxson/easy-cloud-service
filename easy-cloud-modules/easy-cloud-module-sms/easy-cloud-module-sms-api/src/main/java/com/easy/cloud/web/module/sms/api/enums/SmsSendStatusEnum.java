package com.easy.cloud.web.module.sms.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 短信发送状态
 *
 * @author GR
 * @since 2021/1/25 10:56
 */
@Getter
@AllArgsConstructor
public enum SmsSendStatusEnum implements IBaseEnum {
    /**
     * 短信渠道
     */
    UN_SEND(1, "未发送"),
    SUCCESS(2, "发送成功"),
    FAIL(3, "发送失败"),
    ;
    private final int code;
    private final String name;

    /**
     * 根据枚举code获取枚举对象
     *
     * @param code 枚举编码
     * @return java.util.Optional<com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum>
     */
    public static Optional<SmsSendStatusEnum> getInstanceByCode(int code) {
        for (SmsSendStatusEnum value : SmsSendStatusEnum.values()) {
            if (value.code == code) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

}
