package com.easy.cloud.web;

//import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
//import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author GR
 */
@EnableJpaAuditing
@SpringBootApplication
@EnableEasyCloudResourceServer
@EntityScan({"com.easy.cloud.web"})
@EnableJpaRepositories({"com.easy.cloud.web"})
public class EasyCloudServiceStandAloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceStandAloneApplication.class, args);
    }

}
