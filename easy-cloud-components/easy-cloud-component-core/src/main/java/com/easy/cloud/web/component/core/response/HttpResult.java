package com.easy.cloud.web.component.core.response;

import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author GR
 * @date 2021-3-9 15:32
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(staticName = "build")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "响应信息主体", description = "0：成功 1：失败")
public class HttpResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static <T> HttpResult<T> ok() {
        return HttpResult.ok(HttpResultEnum.SUCCESS.getCode(), HttpResultEnum.SUCCESS.getDesc(), null);
    }

    public static <T> HttpResult<T> ok(T data) {
        return HttpResult.ok(HttpResultEnum.SUCCESS.getCode(), HttpResultEnum.SUCCESS.getDesc(), data);
    }

    public static <T> HttpResult<T> ok(String desc, T data) {
        return HttpResult.ok(HttpResultEnum.SUCCESS.getCode(), desc, data);
    }

    public static <T> HttpResult<T> ok(Integer code, String desc, T data) {
        return HttpResult.<T>build().setData(data).setCode(code).setMsg(desc);
    }

    public static <T> HttpResult<T> fail() {
        return HttpResult.fail(HttpResultEnum.FAIL.getCode(), HttpResultEnum.FAIL.getDesc());
    }

    public static <T> HttpResult<T> fail(String desc) {
        return HttpResult.fail(HttpResultEnum.FAIL.getCode(), desc);
    }

    public static <T> HttpResult<T> fail(Integer code, String desc) {
        return HttpResult.<T>build().setCode(code).setMsg(desc);
    }
}
