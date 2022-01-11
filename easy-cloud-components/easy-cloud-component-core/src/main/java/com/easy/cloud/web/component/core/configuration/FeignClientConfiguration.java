package com.easy.cloud.web.component.core.configuration;

import com.easy.cloud.web.component.core.feign.FeignDecoder;
import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Feign Client配置
 *
 * @author GR
 * @date 2021-12-16 23:19
 */
@Configuration
public class FeignClientConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    @Primary
    public Decoder feignDecoder() {
        return new OptionalDecoder(
                new ResponseEntityDecoder(new FeignDecoder(this.messageConverters)));
    }
}
