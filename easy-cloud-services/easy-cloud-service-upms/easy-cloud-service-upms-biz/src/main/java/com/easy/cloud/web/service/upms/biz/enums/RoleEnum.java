package com.easy.cloud.web.service.upms.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型枚举
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 角色枚举类型
     */
    ROLE_SUPER_ADMIN(0, "0", "超级管理员"),
    ROLE_ADMIN(1, "1", "管理员"),
    ROLE_TENANT(2, "2", "租户"),
    ;

    private final int code;
    private final String id;
    private final String desc;
}
