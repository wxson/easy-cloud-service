package com.alibaba.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasyCloudRegisterApplication {

    public static void main(String[] args) {
//        System.setProperty(ConfigConstants.TOMCAT_DIR, "nacos");
//        System.setProperty(ConfigConstants.TOMCAT_ACCESS_LOG, "true");
//        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
//        System.setProperty("nacos.home", "nacos");
        SpringApplication.run(EasyCloudRegisterApplication.class, args);
    }

}
