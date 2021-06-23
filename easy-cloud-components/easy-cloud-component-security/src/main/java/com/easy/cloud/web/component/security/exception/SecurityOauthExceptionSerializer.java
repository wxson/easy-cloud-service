package com.easy.cloud.web.component.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author GR
 * @date 2021-4-14 14:04
 */
public class SecurityOauthExceptionSerializer extends StdSerializer<SecurityOauthException> {
    protected SecurityOauthExceptionSerializer() {
        super(SecurityOauthException.class);
    }

    @Override
    public void serialize(SecurityOauthException securityOauthException, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code", securityOauthException.getCode());
        jsonGenerator.writeStringField("msg", securityOauthException.getMessage());
        if (Objects.nonNull(securityOauthException.getData())) {
            jsonGenerator.writeObjectField("data", securityOauthException.getData());
        }

        jsonGenerator.writeEndObject();
    }
}
