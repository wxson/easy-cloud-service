package com.easy.cloud.web.component.core.configuration;

import com.easy.cloud.web.component.core.enums.deserializer.EnumConverterFactory;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 核心自动配置
 *
 * @author GR
 * @date 2021-3-9 17:59
 */
@Configuration
@ComponentScan({"com.easy.cloud.web.component.core"})
public class CoreAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public SpringContextHolder applicationContextUtils() {
        return new SpringContextHolder();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }
}
