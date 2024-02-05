package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 订单状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements IBaseEnum {
    /**
     * 0->待确认；1->待付款；2->已预付；3->待发货；4->已发货；5->已完成；6->无效订单；7->申请售后；8->售后中；9->完成售后；
     */
    WAI_CONFIRM(0, "待确认"),
    WAIT_PAID(1, "待付款"),
    // 此预付为订单已调取支付接口,但未回调支付成功
    PRE_PAID(2, "已预付"),
    WAIT_SHIPPED(3, "待发货"),
    SHIPPED(4, "已发货"),
    COMPLETE(5, "已完成"),
    INVALID(6, "无效订单"),
    APPLY_AFTER_SERVICE(7, "申请售后"),
    AFTER_SERVICE(8, "售后中"),
    COMPLETE_AFTER_SERVICE(9, "完成售后"),
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
     * 根据code获取对象
     *
     * @param code 编码
     * @return java.util.Optional<com.easy.cloud.web.service.mj.biz.enums.HuCardRuleEnum>
     */
    public static Optional<OrderStatusEnum> getInstanceByCode(int code) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (code == orderStatusEnum.code) {
                return Optional.of(orderStatusEnum);
            }
        }
        return Optional.empty();
    }
}
