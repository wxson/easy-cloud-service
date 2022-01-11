package com.easy.cloud.web.component.security.feign;

import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 配置增强
 *
 * @author GR
 * @date 2021-3-26 17:12
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignClientInterceptor();
    }

}
