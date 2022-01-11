package com.easy.cloud.web.service.order.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 完成状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum FinishStatusEnum implements IBaseEnum {
    /**
     * 未完成（UNFINISHED），已完成（FINISHED）。
     */
    UNFINISHED(1, "未完成"),
    FINISHED(2, "已完成"),
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
    public static Optional<FinishStatusEnum> getInstanceByCode(int code) {
        for (FinishStatusEnum orderStatusEnum : FinishStatusEnum.values()) {
            if (code == orderStatusEnum.code) {
                return Optional.of(orderStatusEnum);
            }
        }
        return Optional.empty();
    }
}
