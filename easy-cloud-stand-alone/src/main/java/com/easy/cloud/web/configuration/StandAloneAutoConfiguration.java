package com.easy.cloud.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 单机部署配置项
 *
 * @author GR
 * @date 2024/2/22 9:35
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.easy.cloud.web"})
public class StandAloneAutoConfiguration {

}
