package com.easy.cloud.web.service.minio.biz.configuration;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GR
 * @date 2021-3-9 17:59
 */
@Slf4j
@RefreshScope
@Configuration
public class MinioAutoConfiguration {

  @Value("${minio.url}")
  private String url;
  @Value("${minio.accessKey}")
  private String accessKey;
  @Value("${minio.secretKey}")
  private String secretKey;

  @Bean
  public MinioClient getMinioClient() {
    return MinioClient.builder()
        .endpoint(url)
        .credentials(accessKey, secretKey)
        .build();
  }
}
