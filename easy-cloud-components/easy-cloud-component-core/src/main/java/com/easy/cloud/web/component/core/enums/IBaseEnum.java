package com.easy.cloud.web.component.core.enums;

import com.easy.cloud.web.component.core.enums.deserializer.IBaseEnumDeserializer;
import com.easy.cloud.web.component.core.enums.serializer.IBaseEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 请求自动序列化转枚举
 *
 * @author GR
 * @date 2021-4-15 13:37
 */
@JsonDeserialize(using = IBaseEnumDeserializer.class)
@JsonSerialize(using = IBaseEnumSerializer.class)
public interface IBaseEnum {
    /**
     * 通用的Code
     *
     * @return code值
     */
    int getCode();
}
