package com.easy.cloud.web.service.member.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 积分业务类型枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum PointsBizTypeEnum implements IBaseEnum {
    /**
     * 交易...
     */
    TRADE(1, "交易"),
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
    public static Optional<PointsBizTypeEnum> getInstanceByCode(int code) {
        for (PointsBizTypeEnum pointsBizTypeEnum : PointsBizTypeEnum.values()) {
            if (code == pointsBizTypeEnum.code) {
                return Optional.of(pointsBizTypeEnum);
            }
        }
        return Optional.empty();
    }
}
