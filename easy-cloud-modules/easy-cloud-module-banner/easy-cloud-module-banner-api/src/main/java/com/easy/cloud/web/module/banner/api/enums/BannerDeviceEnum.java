package com.easy.cloud.web.module.banner.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 广告轮播图终端
 *
 * @author GR
 * @date 2024-3-2 23:45
 */
@Getter
@AllArgsConstructor
public enum BannerDeviceEnum implements IBaseEnum {
    /**
     * 广告轮播图终端
     */
    APP(1, "APP"),
    H5(2, "H5"),
    PC(3, "PC"),
    APPLET(4, "小程序"),
    ;

    private final int code;
    private final String desc;
}
