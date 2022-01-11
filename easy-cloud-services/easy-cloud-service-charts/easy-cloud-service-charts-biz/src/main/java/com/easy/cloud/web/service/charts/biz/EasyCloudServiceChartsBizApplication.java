package com.easy.cloud.web.service.charts.biz;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author GR
 */
@EnableSwagger2
@SpringBootApplication
@EnableEasyCloudFeignClients
@EnableEasyCloudResourceServer
public class EasyCloudServiceChartsBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceChartsBizApplication.class, args);
    }

}
