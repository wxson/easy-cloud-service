package com.easy.cloud.web.module.sms.biz.service;

import com.aliyuncs.exceptions.ClientException;
import com.easy.cloud.web.module.sms.api.dto.SmsSendDTO;
import com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum;
import com.easy.cloud.web.module.sms.api.vo.SmsSendVO;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

/**
 * @author GR
 * @date 2024-1-28 23:01
 */
public interface ISmsClient {
    /**
     * 短信渠道
     *
     * @return com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum
     */
    SmsChannelEnum channel();

    /**
     * 发送单条短信
     *
     * @param smsSendDTO 短信发送数据
     * @return com.easy.cloud.web.module.sms.api.vo.SmsSendVO
     */
    SmsSendVO sendSingleSms(SmsSendDTO smsSendDTO) throws ClientException, TencentCloudSDKException;
}
