package com.easy.cloud.web.module.sms.biz.service.client.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author GR
 * @date 2024-1-28 23:11
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "easy-cloud.sms")
public class SmsProperties {
    /**
     * 短信签名
     */
    private String signature;
    /**
     * 渠道编码
     */
    private String channel;
    /**
     * 短信 API 的账号
     */
    private String apiKey;
    /**
     * 短信 API 的密钥
     */
    private String apiSecret;
    /**
     * 短信区域
     */
    private String endpoint;
    /**
     * 验证码长度
     */
    private Integer codeLength;
    /**
     * 腾讯云应用ID
     */
    private String appId;
    /**
     * 模板Code
     */
    private String templateCode;
}
