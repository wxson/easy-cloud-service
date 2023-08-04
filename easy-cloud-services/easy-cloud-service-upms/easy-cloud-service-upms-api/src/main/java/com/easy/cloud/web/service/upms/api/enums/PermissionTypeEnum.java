package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限类型枚举
 * permission
 * 包含：MenuPermission、ApplicationPermission、NavPermission、ApiPermission....
 *
 * @author GR
 * @date 2020-11-13 13:44
 */
@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {
    /**
     * 菜单权限类型
     */
    MENU(1, "菜单权限", true, true),
    /**
     * 应用权限
     */
    APPLICATION(2, "应用权限", true, true),
    /**
     * API权限
     */
    API(3, "API权限", true, false),
    /**
     * 资源权限
     */
    RESOURCE(4, "资源权限", true, true),
    /**
     * 页面访问权限
     */
    PAGE(5, "页面访问权限", true, false),
    /**
     * 微服务模块权限
     */
    SERVICE(6, "微服务模块权限", true, false),
    /**
     * 付费资源，单个产品付费
     */
    VIP_RESOURCE(7, "付费资源权限", false, true),
    /**
     * 付费功能
     */
    VIP_DEMAND(8, "付费功能权限", false, true),
    ;
    private final int code;
    private final String desc;
    /**
     * 是否免费
     */
    private final Boolean free;
    /**
     * 是否支持action操作
     */
    private final Boolean supportAction;
}
