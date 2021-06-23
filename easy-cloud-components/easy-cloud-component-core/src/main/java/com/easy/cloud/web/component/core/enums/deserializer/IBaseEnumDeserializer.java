package com.easy.cloud.web.component.core.enums.deserializer;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * 枚举反序列化
 *
 * @author GR
 * @date 2021-4-19 10:05
 */
public class IBaseEnumDeserializer extends JsonDeserializer<IBaseEnum> {
    @Override
    public IBaseEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int code = node.asInt();
        String currentName = jsonParser.getCurrentName();
        Object currentValue = jsonParser.getCurrentValue();
        Class findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        Object[] enumConstants = findPropertyType.getEnumConstants();
        for (Object o : enumConstants) {
            if (o instanceof IBaseEnum) {
                IBaseEnum enumConstant = (IBaseEnum) o;
                if (code == enumConstant.getCode()) {
                    return enumConstant;
                }
            }
        }
        return null;
    }
}