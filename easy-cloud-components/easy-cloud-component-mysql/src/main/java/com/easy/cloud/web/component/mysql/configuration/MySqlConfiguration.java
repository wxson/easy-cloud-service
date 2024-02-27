package com.easy.cloud.web.component.mysql.configuration;

import com.easy.cloud.web.component.core.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置租户
 *
 * @author GR
 * @date 2021-10-28 18:21
 */
@Slf4j
@Configuration
@ComponentScan({"com.easy.cloud.web.component.mysql"})
public class MySqlConfiguration {

    @Bean
    public IdentifierGenerator identifierGenerator() {
        return (sharedSessionContractImplementor, o) -> {
            return SnowflakeUtils.next();
        };
    }
}
