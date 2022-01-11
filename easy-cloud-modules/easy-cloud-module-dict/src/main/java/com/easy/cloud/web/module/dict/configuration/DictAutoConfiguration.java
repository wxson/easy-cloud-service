package com.easy.cloud.web.module.dict.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author GR
 * @date 2021-3-9 17:59
 */
@Configuration
@MapperScan("com.easy.cloud.web.module.dict.mapper")
@ComponentScan({"com.easy.cloud.web.module.dict","com.easy.cloud.web.module.dict.controller"})
public class DictAutoConfiguration {

}
