package com.easy.cloud.web.service.minio.biz.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author GR
 * @date 2024/1/29 12:02
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * 地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * accessKey
     */
    @Value("${minio.accessKey}")
    private String accessKey;
    /**
     * secretKey
     */
    @Value("${minio.secretKey}")
    private String secretKey;
}
