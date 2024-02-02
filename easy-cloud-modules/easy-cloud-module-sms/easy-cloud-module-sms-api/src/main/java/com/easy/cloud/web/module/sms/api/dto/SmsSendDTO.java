package com.easy.cloud.web.module.sms.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

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
public class SmsSendDTO {
    /**
     * 短信接收电话
     */
    private String tel;
    /**
     * 短信签名
     */
    private String signature;
    /**
     * 短信模板ID
     */
    private String templateId;
    /**
     * 短信内容
     */
    private Map<String, Object> content;
    /**
     * 短信记录ID
     */
    private String recordId;
}