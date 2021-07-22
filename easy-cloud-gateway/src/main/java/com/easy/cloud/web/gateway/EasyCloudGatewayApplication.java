package com.easy.cloud.web.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author GR
 */
@SpringCloudApplication
public class EasyCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudGatewayApplication.class, args);
    }

}
