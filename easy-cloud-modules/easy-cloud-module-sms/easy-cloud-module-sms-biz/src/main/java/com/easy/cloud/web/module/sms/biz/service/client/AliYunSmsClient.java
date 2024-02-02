package com.easy.cloud.web.module.sms.biz.service.client;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.module.sms.api.dto.SmsSendDTO;
import com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum;
import com.easy.cloud.web.module.sms.api.vo.SmsSendVO;
import com.easy.cloud.web.module.sms.biz.service.ISmsClient;
import com.easy.cloud.web.module.sms.biz.service.client.properties.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 阿里云短信客户端
 *
 * @author GR
 * @date 2024-1-28 23:06
 */
@Slf4j
@Service
public class AliYunSmsClient implements ISmsClient {

    @Autowired
    private SmsProperties smsProperties;

    /**
     * 阿里云客户端
     */
    private volatile IAcsClient client;

    @PostConstruct
    public void doInit() {
        if (Objects.nonNull(smsProperties)) {
            IClientProfile profile = DefaultProfile.getProfile(smsProperties.getEndpoint(),
                    smsProperties.getApiKey(), smsProperties.getApiSecret());
            this.client = new DefaultAcsClient(profile);
        }
    }

    @Override
    public SmsChannelEnum channel() {
        return SmsChannelEnum.ALI_YUN;
    }

    @Override
    public SmsSendVO sendSingleSms(SmsSendDTO smsSendDTO) throws ClientException {
        // 客户端未初始化
        if (Objects.isNull(client)) {
            throw new BusinessException("发送短信失败，当前阿里云短信客户端未初始化");
        }

        // 构建请求
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(smsSendDTO.getTel());
        request.setSignName(smsSendDTO.getSignature());
        request.setTemplateCode(smsSendDTO.getTemplateId());
        request.setTemplateParam(JSONUtil.toJsonStr(smsSendDTO.getContent()));
        request.setOutId(smsSendDTO.getRecordId());
        // 执行请求
        SendSmsResponse response = client.getAcsResponse(request);
        return SmsSendVO.builder()
                .requestId(response.getRequestId())
                .bizId(response.getBizId())
                .code(response.getCode())
                .message(response.getMessage())
                .recordId(smsSendDTO.getRecordId())
                .build();
    }
}
