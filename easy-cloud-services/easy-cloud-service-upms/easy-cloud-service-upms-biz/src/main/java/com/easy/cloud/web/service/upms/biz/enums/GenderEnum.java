package com.easy.cloud.web.service.upms.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 人的性别代码
 *
 * @author GR
 * @date 2020-9-22 16:44
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    /**
     * 未知的性别
     */
    UN_KNOWN("0", "未知的性别"),
    /**
     * 男性
     */
    MAN("1", "男性"),
    /**
     * 女性
     */
    WOMAN("2", "女性"),
    /**
     * 未说明的性别
     */
    UN_SPECIFIED("9", "未说明的性别");
    private final String code;
    private final String desc;
}
