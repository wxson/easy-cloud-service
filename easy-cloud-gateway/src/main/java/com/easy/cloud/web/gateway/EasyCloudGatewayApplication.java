package com.easy.cloud.web.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author GR
 */
@CrossOrigin
@EnableOpenApi
@SpringCloudApplication
public class EasyCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudGatewayApplication.class, args);
    }

}
