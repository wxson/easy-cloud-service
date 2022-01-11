package com.easy.cloud.web.oauth;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author GR
 */
@SpringBootApplication
@EnableEasyCloudFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EasyCloudOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudOauthApplication.class, args);
    }

}
