package com.easy.cloud.web.component.core.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回结果枚举
 *
 * @author GR
 * @date 2021-3-9 15:22
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "", description = "HTTP返回状态枚举")
public enum HttpResultEnum implements IBaseEnum {
    /**
     * http响应枚举类
     */
    SUCCESS(0, "请求成功"),
    FAIL(1, "请求失败"),
    USER_PASSWORD(2001, "用户名或密码错误！"),
    TOKEN_EXPIRED(2002, "登录凭证已过期！"),
    REPEAT_SUBMIT(2003, "系统正在处理、请勿重复提交!"),
    REQUEST_LIMIT(2004, "你的操作过于频繁,请休息一下吧!"),
    MULTI_ROLE(2005, "请选择角色登录"),
    ;
    private final int code;
    private final String desc;
}
