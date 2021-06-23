package com.easy.cloud.web.component.core.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DeletedEnum：0：未删除 1：已删除
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "", description = "是否删除枚举：0 未删除 1 已删除")
public enum DeletedEnum implements IBaseEnum {
    /**
     * DeletedEnum：0：未删除 1：已删除
     */
    UN_DELETED(0, "未删除"),
    DELETED(1, "已删除"),
    ;

    private final int code;
    private final String desc;
}
