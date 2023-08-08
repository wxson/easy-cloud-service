package com.easy.cloud.web.service.minio.biz;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GR
 */
@SpringBootApplication
@EnableEasyCloudFeignClients
@EnableEasyCloudResourceServer
public class EasyCloudServiceMinioBizApplication {

  public static void main(String[] args) {
    SpringApplication.run(EasyCloudServiceMinioBizApplication.class, args);
  }

}
