package com.easy.cloud.web.service.member.biz;

import com.easy.cloud.web.component.security.annotation.EnableEasyCloudFeignClients;
import com.easy.cloud.web.component.security.annotation.EnableEasyCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("com.easy.cloud.web.service.member.biz.mapper")
public class EasyCloudServiceMemberBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceMemberBizApplication.class, args);
    }

}
