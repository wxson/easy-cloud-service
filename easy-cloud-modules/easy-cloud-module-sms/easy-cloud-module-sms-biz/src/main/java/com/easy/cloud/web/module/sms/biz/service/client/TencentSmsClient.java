package com.easy.cloud.web.module.sms.biz.service.client;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.module.sms.api.dto.SmsSendDTO;
import com.easy.cloud.web.module.sms.api.enums.SmsChannelEnum;
import com.easy.cloud.web.module.sms.api.vo.SmsSendVO;
import com.easy.cloud.web.module.sms.biz.service.ISmsClient;
import com.easy.cloud.web.module.sms.biz.service.client.properties.SmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

/**
 * 腾讯云短信客户端
 *
 * @author GR
 * @date 2024-1-28 23:06
 */
@Slf4j
@Service
public class TencentSmsClient implements ISmsClient {

    @Autowired
    private SmsProperties smsProperties;

    /**
     * 腾讯云客户端
     */
    private volatile SmsClient client;

    @PostConstruct
    public void doInit() {
        if (Objects.nonNull(smsProperties)) {
            // 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId，secretKey
            Credential credential = new Credential(smsProperties.getApiKey(), smsProperties.getApiSecret());
            // 实例化一个http选项，可选，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理（无需要直接忽略）
            // httpProfile.setProxyHost("真实代理ip");
            // httpProfile.setProxyPort(真实代理端口);
            /* SDK默认使用POST方法。
             * 如果您一定要使用GET方法，可以在这里设置。GET方法无法处理一些较大的请求 */
            httpProfile.setReqMethod("POST");
            /* SDK有默认的超时时间，非必要请不要进行调整
             * 如有需要请在代码中查阅以获取最新的默认值 */
            httpProfile.setConnTimeout(60);
            /* 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com ，也支持指定地域域名访问，例如广州地域的域名为 sms.ap-guangzhou.tencentcloudapi.com */
            httpProfile.setEndpoint(smsProperties.getEndpoint());


            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名
             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            this.client = new SmsClient(credential, "ap-guangzhou", clientProfile);
        }
    }

    @Override
    public SmsChannelEnum channel() {
        return SmsChannelEnum.TENCENT;
    }

    @Override
    public SmsSendVO sendSingleSms(SmsSendDTO smsSendDTO) throws TencentCloudSDKException {
        // 客户端未初始化
        if (Objects.isNull(client)) {
            throw new BusinessException("发送短信失败，当前腾讯云短信客户端未初始化");
        }

        // 构建请求
        SendSmsRequest request = new SendSmsRequest();
        request.setSmsSdkAppId(smsProperties.getAppId());
        request.setSignName(smsProperties.getSignature());
        request.setTemplateId(smsProperties.getTemplateCode());
        request.setPhoneNumberSet(new String[]{"+86" + smsSendDTO.getTel()});
        Map<String, Object> content = smsSendDTO.getContent();
        // 验证码、3分组
        request.setTemplateParamSet(new String[]{content.get("code").toString(), "3"});
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.putOpt("recordId", smsSendDTO.getRecordId());
        request.setSessionContext(JSONUtil.toJsonStr(jsonObject));
        // 执行请求
        SendSmsResponse response = client.SendSms(request);
        SendStatus status = response.getSendStatusSet()[0];
        return SmsSendVO.builder()
                .requestId(response.getRequestId())
                .bizId(status.getSerialNo())
                .code(status.getCode())
                .message(status.getMessage())
                .recordId(smsSendDTO.getRecordId())
                .build();
    }
}
