package com.easy.cloud.web.service.cms.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 全局配置枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum GlobalConfEnum implements IBaseEnum {
    /**
     * 全局配置权限GlobalConfEnum
     */
    ENABLE_MEMBER_STORAGE_PERMISSION_GOODS_NO(1, "enable_storage_goods_no", "允许启用仓库权限的商品编码"),
    BIND_FRIEND_GOODS_NO(2, "bind_friend_goods_no", "绑定好友获取商品编码"),
    BIND_TEL_GOODS_NO(3, "bind_tel_goods_no", "绑定电话获取商品编码"),
    CERTIFICATION_GOODS_NO(4, "certification_goods_no", "实名认证获取商品编码"),
    ;

    /**
     * 编码
     */
    private final int code;
    /**
     * 全局配置key
     */
    private final String key;
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
    public static Optional<GlobalConfEnum> getInstanceByCode(int code) {
        for (GlobalConfEnum globalConfEnum : GlobalConfEnum.values()) {
            if (code == globalConfEnum.code) {
                return Optional.of(globalConfEnum);
            }
        }
        return Optional.empty();
    }
}
