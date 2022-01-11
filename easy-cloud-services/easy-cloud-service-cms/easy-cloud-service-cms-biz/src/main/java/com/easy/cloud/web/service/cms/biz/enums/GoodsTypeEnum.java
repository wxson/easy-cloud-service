package com.easy.cloud.web.service.cms.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 商品类型
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum GoodsTypeEnum implements IBaseEnum {
    /**
     * 商品类型
     */
    DIAMOND(1, "钻石"),
    GOLD_COIN(2, "金币"),
    COUPON(3, "点券"),
    TABLECLOTH(4, "桌布"),
    CARD_BACK(5, "牌背"),
    DIAMOND_GOLD_COIN(6, "钻石+金币"),
    DIAMOND_COUPON(7, "钻石+点券"),
    GOLD_COIN_COUPON(8, "金币+点券"),
    DIAMOND_GOLD_COIN_COUPON(9, "钻石+金币+点券"),
    PROP_EXCHANGE(10, "道具兑换"),
    GOLD_COIN_ALIVENESS(11, "金币+活跃度"),
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
    public static Optional<GoodsTypeEnum> getInstanceByCode(int code) {
        for (GoodsTypeEnum goodsTypeEnum : GoodsTypeEnum.values()) {
            if (code == goodsTypeEnum.code) {
                return Optional.of(goodsTypeEnum);
            }
        }
        return Optional.empty();
    }
}
