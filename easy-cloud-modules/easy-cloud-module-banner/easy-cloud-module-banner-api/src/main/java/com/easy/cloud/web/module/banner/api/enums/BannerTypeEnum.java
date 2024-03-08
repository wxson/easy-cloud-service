package com.easy.cloud.web.module.banner.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 广告轮播图类型
 *
 * @author GR
 * @date 2024-3-2 23:45
 */
@Getter
@AllArgsConstructor
public enum BannerTypeEnum implements IBaseEnum {
    /**
     * 广告轮播图类型：1、外部链接
     */
    IMAGE(1, "图片"),
    LINK(2, "外部链接"),
    ARTICLE(3, "文章"),
    DIALOG(4, "弹窗"),
    ;

    private final int code;
    private final String desc;
}
