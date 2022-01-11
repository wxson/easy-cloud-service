package com.easy.cloud.web.service.charts.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author GR
 * @date 2021-12-26 16:47
 */
@Getter
@AllArgsConstructor
public enum PanelEnum {
    /**
     * 面板类型
     */
    ONLINE(1, "在线人数"),
    EARNINGS(2, "平台收益"),
    MEMBERS(3, "注册人数"),
    GOODS(4, "商品登记"),
    ;
    /**
     * 类型
     */
    private int code;
    /**
     * 描述
     */
    private String desc;
}
