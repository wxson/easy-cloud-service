package com.easy.cloud.web.module.sms.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author GR
 * @date 2021-3-9 17:59
 */
@Configuration
@ComponentScan({"com.easy.cloud.web.module.sms", "com.easy.cloud.web.module.sms.controller"})
public class SmsAutoConfiguration {

}
