package io.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GR
 */
@SpringBootApplication(scanBasePackages = {"io.seata.console"})
public class EasyCloudServiceSeataApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceSeataApplication.class, args);
    }

}
