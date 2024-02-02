package com.easy.cloud.web.module.sms.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Sms发送数据
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SmsValidateCodeDTO {
    /**
     * 短信接收电话
     */
    private String tel;
    /**
     * 短信验证码
     */
    private String code;
}