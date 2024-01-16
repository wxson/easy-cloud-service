package com.easy.cloud.web.component.core.configuration;

import com.easy.cloud.web.component.core.enums.deserializer.EnumConverterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author GR
 * @date 2024/1/16 9:53
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }
}
