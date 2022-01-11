package com.easy.cloud.web.service.upms.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限操作枚举
 * permission
 * 包含：MenuPermission、ApplicationPermission、NavPermission、ApiPermission....
 *
 * @author GR
 * @date 2020-11-13 13:44
 */
@Getter
@AllArgsConstructor
public enum PermissionActionEnum {
    /**
     * 查看权限
     */
    LOOK(1, "LOOK", "", false, "查看权限"),
    /**
     * 新增权限
     */
    ADD(2, "ADD", "", false, "新增权限"),
    /**
     * 删除权限
     */
    DELETE(3, "DELETE", "", true, "删除权限"),
    /**
     * 更新权限
     */
    UPDATE(4, "UPDATE", "", true, "更新权限"),
    /**
     * 搜索权限
     */
    FIND(5, "FIND", "", false, "搜索权限"),
    /**
     * 上传下载
     */
    UPLOAD(6, "UPLOAD", "", true, "上传下载"),
    /**
     * 下载权限
     */
    DOWNLOAD(7, "DOWNLOAD", "", true, "下载权限"),
    ;
    private final int code;
    /**
     * 字段
     */
    private final String filed;
    /**
     * 图标
     */
    private final String icon;
    /**
     * 默认状态，当前状态为true时，用户操作时需重新娇艳是否又该权限
     */
    private final Boolean defaultCheck;
    /**
     * 描述
     */
    private final String desc;
}
