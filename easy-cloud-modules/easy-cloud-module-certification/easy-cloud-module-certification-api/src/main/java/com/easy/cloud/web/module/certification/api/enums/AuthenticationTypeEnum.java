package com.easy.cloud.web.module.certification.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 审核类型
 *
 * @author GR
 * @date 2024/2/19 11:34
 */
@Getter
@AllArgsConstructor
public enum AuthenticationTypeEnum implements IBaseEnum {
    /**
     * 审核状态：1-企业；2-个人；
     */
    Company(1, "企业"),
    Personal(2, "个人"),
    ;
    private final int code;
    private final String desc;


    /**
     * 根据code获取对象
     *
     * @param code 编码
     * @return
     */
    public static Optional<AuthenticationTypeEnum> getInstanceByCode(int code) {
        for (AuthenticationTypeEnum authenticationTypeEnum : AuthenticationTypeEnum.values()) {
            if (code == authenticationTypeEnum.code) {
                return Optional.of(authenticationTypeEnum);
            }
        }
        return Optional.empty();
    }
}
