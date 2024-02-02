package com.easy.cloud.web.module.sms.api.dto;

import com.easy.cloud.web.module.sms.api.enums.SmsSendStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Sms请求数据
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SmsDTO {

    /**
     * 文档ID
     */
    private String id;
    /**
     * 短信验证码
     */
    private String code;
    /**
     * 短信接收电话
     */
    private String tel;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 是否已使用，默认未使用
     */
    private Boolean used;
    /**
     * 短信发送状态，默认未发送
     */
    private SmsSendStatusEnum sendStatus;
    /**
     * 备注描述
     */
    private String remark;
}