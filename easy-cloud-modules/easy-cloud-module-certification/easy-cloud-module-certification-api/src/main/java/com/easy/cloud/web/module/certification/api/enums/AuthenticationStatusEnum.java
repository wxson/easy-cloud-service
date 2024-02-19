package com.easy.cloud.web.module.certification.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 审核状态
 *
 * @author GR
 * @date 2024/2/19 11:34
 */
@Getter
@AllArgsConstructor
public enum AuthenticationStatusEnum implements IBaseEnum {
    /**
     * 审核状态：1-待审核；2-审核通过；3-审核失败；
     */
    WAIT(1, "待审核"),
    SUCCESS(2, "审核通过"),
    FAIL(3, "审核失败"),
    ;
    private final int code;
    private final String desc;


    /**
     * 根据code获取对象
     *
     * @param code 编码
     * @return
     */
    public static Optional<AuthenticationStatusEnum> getInstanceByCode(int code) {
        for (AuthenticationStatusEnum authenticationStatusEnum : AuthenticationStatusEnum.values()) {
            if (code == authenticationStatusEnum.code) {
                return Optional.of(authenticationStatusEnum);
            }
        }
        return Optional.empty();
    }
}
