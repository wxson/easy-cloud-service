package com.easy.cloud.web.service.upms.biz;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GR
 */
@SpringBootApplication
@EnableEasyCloudFeignClients
@EnableEasyCloudResourceServer
@MapperScan("com.easy.cloud.web.service.upms.biz.mapper")
public class EasyCloudServiceUpmsBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceUpmsBizApplication.class, args);
    }

}
