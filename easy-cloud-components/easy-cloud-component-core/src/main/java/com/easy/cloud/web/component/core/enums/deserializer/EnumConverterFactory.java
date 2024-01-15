package com.easy.cloud.web.component.core.enums.deserializer;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author GR
 * @date 2024/1/15 19:30
 */
public class EnumConverterFactory implements ConverterFactory<String, IBaseEnum> {

    @Override
    public <T extends IBaseEnum> Converter<String, T> getConverter(Class<T> enumClass) {
        return code -> {
            for (T enumConstant : enumClass.getEnumConstants()) {
                if (enumConstant.getCode() == Integer.parseInt(code)) {
                    return enumConstant;
                }
            }
            return null;
        };
    }
}
