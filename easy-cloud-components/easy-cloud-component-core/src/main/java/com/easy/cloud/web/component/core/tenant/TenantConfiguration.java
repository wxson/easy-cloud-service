package com.easy.cloud.web.component.core.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * @author GR
 * @date 2021-4-7 10:19
 */
@Configuration
public class TenantConfiguration {

    @Bean
    public RequestInterceptor feignTenantInterceptor() {
        return new FeignTenantInterceptor();
    }

    @Bean
    public ClientHttpRequestInterceptor tenantRequestInterceptor() {
        return new TenantRequestInterceptor();
    }

}
