package com.easy.cloud.web.service.cms.biz.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 商品分类
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum GoodsClassifyEnum implements IBaseEnum {
    /**
     * 商品分类
     */
    SHOP(1, "商店商品"),
    ACTION(2, "活动商品"),
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
    public static Optional<GoodsClassifyEnum> getInstanceByCode(int code) {
        for (GoodsClassifyEnum goodsClassifyEnum : GoodsClassifyEnum.values()) {
            if (code == goodsClassifyEnum.code) {
                return Optional.of(goodsClassifyEnum);
            }
        }
        return Optional.empty();
    }
}
