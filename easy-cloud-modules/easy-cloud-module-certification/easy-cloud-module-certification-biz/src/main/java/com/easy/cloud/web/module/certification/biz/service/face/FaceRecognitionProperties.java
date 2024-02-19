package com.easy.cloud.web.module.certification.biz.service.face;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 人脸识别配置
 *
 * @author GR
 * @date 2024/2/19 19:47
 */
@Getter
@Setter
@Component
@RefreshScope
@ConfigurationProperties(prefix = "easy-cloud.face-recognition")
public class FaceRecognitionProperties {

    /**
     * 渠道编码:ALI-阿里云/TENCENT-腾讯云
     */
    private String channel;

    /**
     * 云市场分配的密钥Id
     */
    private String secretId;
    /**
     * 云市场分配的密钥Key
     */
    private String secretKey;
}
