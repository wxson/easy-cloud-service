package com.easy.cloud.web.module.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.cloud.web.module.sms.domain.db.SmsSendRecordDO;
import com.easy.cloud.web.module.sms.domain.dto.SmsDTO;

/**
 * @author GR
 * @date 2021-12-9 19:03
 */
public interface ISmsSendRecordService extends IService<SmsSendRecordDO> {

    /**
     * 短信回调
     *
     * @param smsDTO 回调信息
     * @return java.lang.Object
     */
    Boolean callBack(SmsDTO smsDTO);

    /**
     * 绑定手机号
     *
     * @param smsDTO 绑定手机号
     * @return java.lang.Boolean
     */
    Boolean bindTel(SmsDTO smsDTO);

    /**
     * 获取手机验证码
     *
     * @param smsDTO 验证手机验证码
     * @return java.lang.Boolean
     */
    Boolean getCodeByTel(SmsDTO smsDTO);
}
