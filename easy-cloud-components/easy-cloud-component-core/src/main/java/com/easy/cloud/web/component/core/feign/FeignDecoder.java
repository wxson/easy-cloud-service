package com.easy.cloud.web.component.core.feign;

import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import feign.FeignException;
import feign.Response;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Feign 解码器
 *
 * @author GR
 * @date 2021-12-16 23:21
 */
public class FeignDecoder extends SpringDecoder {

    public FeignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Object decode = super.decode(response, type);
        HttpResult httpResult = JSONUtil.toBean(JSONUtil.toJsonStr(decode), HttpResult.class);
        // 不是成功状态
        if (!httpResult.getCode().toString().equals("0")) {
            throw new BusinessException(httpResult.getCode(), httpResult.getMessage());
        }

        return decode;
    }
}
