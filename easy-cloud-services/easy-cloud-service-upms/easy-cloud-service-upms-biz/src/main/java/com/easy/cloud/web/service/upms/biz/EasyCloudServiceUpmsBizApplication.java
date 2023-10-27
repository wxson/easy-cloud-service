package com.easy.cloud.web.service.upms.biz;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author GR
 */
@EnableJpaAuditing
@SpringBootApplication
@EnableEasyCloudFeignClients
@EnableEasyCloudResourceServer
@EnableTransactionManagement
@EntityScan({"com.easy.cloud.web"})
@EnableJpaRepositories({"com.easy.cloud.web"})
@ComponentScan({"com.easy.cloud.web"})
public class EasyCloudServiceUpmsBizApplication {

  public static void main(String[] args) {
    SpringApplication.run(EasyCloudServiceUpmsBizApplication.class, args);
  }

}
