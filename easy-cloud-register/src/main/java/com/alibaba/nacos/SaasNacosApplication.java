package com.alibaba.nacos;

//import com.alibaba.nacos.console.config.ConfigConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaasNacosApplication {

    public static void main(String[] args) {
//        System.setProperty(ConfigConstants.TOMCAT_DIR, "nacos");
//        System.setProperty(ConfigConstants.TOMCAT_ACCESS_LOG, "true");
//        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
//        System.setProperty("nacos.home", "nacos");
        SpringApplication.run(SaasNacosApplication.class, args);
    }

}
