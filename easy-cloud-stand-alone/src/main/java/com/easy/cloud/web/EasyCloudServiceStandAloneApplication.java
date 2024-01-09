package com.easy.cloud.web;

//import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
//import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GR
 */
//@EnableJpaAuditing
@SpringBootApplication
//@EnableEasyCloudResourceServer
//@EntityScan({"com.easy.cloud.web"})
//@EnableJpaRepositories({"com.easy.cloud.web"})
//@ComponentScan(basePackages = {"com.easy.cloud.web"})
public class EasyCloudServiceStandAloneApplication {

  public static void main(String[] args) {
    SpringApplication.run(EasyCloudServiceStandAloneApplication.class, args);
  }

}
