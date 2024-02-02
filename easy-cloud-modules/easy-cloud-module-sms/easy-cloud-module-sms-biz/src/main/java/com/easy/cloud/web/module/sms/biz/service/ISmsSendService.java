package com.easy.cloud.web.module.sms.biz.service;

import com.easy.cloud.web.module.sms.api.dto.SmsValidateCodeDTO;

/**
 * 短信发送逻辑
 *
 * @author GR
 * @date 2024-1-28 23:18
 */
public interface ISmsSendService {

    /**
     * 发送短信
     *
     * @param tel 短信接收电话
     */
    void sendSms(String tel);

    /**
     * 校验短信验证码
     *
     * @param smsValidateCodeDTO 验证码信息
     */
    void validateSmsCode(SmsValidateCodeDTO smsValidateCodeDTO);
}
