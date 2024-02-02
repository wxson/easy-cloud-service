package com.easy.cloud.web.module.sms.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * SmsSend展示数据
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SmsSendVO {
    /**
     * 请求ID
     */
    private String requestId;
    /**
     * 短信回执ID，即流水号
     */
    private String bizId;
    /**
     * 短信状态码
     */
    private String code;
    /**
     * 短信内容
     */
    private String message;
    /**
     * 短信记录ID
     */
    private String recordId;
}