package com.easy.cloud.web.component.mysql.configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置租户
 *
 * @author GR
 * @date 2021-10-28 18:21
 */
@Configuration
@ComponentScan({"com.easy.cloud.web.component.mysql"})
public class MySqlConfiguration {

    /**
     * 拦截器
     *
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户插件
//        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
//        tenantInterceptor.setTenantLineHandler(() -> {
//            // 返回当前用户的租户ID
//            return new LongValue(SecurityUtils.getAuthenticationUser().getTenantId());
//        });
//        interceptor.addInnerInterceptor(tenantInterceptor);
        // 分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
