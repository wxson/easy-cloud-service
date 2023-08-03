package com.easy.cloud.web.component.mongo.configuration;

import com.easy.cloud.web.component.mongo.interceptor.MongoInterceptorHandler;
import com.easy.cloud.web.component.mongo.interceptor.MongoInterceptorProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

/**
 * @author GR
 * @date 2023/8/3 10:35
 */
@Configuration
@ComponentScan("com.easy.cloud.web.component.mongo")
public class MongoConfiguration {

  @Bean
  public BeforeConvertCallback beforeConvertCallback(
      MongoInterceptorProperty mongoInterceptorProperty) {
    return new MongoInterceptorHandler(mongoInterceptorProperty);
  }

}
