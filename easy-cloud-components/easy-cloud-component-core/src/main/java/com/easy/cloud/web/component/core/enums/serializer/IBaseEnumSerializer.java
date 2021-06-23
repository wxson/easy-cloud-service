package com.easy.cloud.web.component.core.enums.serializer;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 将返回给前端的枚举值自动转为对应的code
 *
 * @author GR
 * @date 2021-4-19 10:05
 */
public class IBaseEnumSerializer extends JsonSerializer<IBaseEnum> {
    @Override
    public void serialize(IBaseEnum iBaseEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        serializerProvider.defaultSerializeValue(iBaseEnum.getCode(), jsonGenerator);
    }
}
