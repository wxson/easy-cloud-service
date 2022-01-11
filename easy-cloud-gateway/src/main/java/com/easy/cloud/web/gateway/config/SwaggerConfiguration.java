package com.easy.cloud.web.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author GR
 * @date 2021-12-1 16:02
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.easy.cloud.web";
    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //api接口包扫描路径
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                //可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档的标题
                .title("Easy Cloud 接口文档示例")
                //设置文档的描述->1.Overview
                .description("更多内容请关注：https://www.anseon.cn")
                //设置文档的版本信息-> 1.1 Version information
                .version(VERSION)
                //设置文档的联系方式->1.2 Contact information
                .contact(new Contact("Easy Cloud", "https://www.anseon.cn", ""))
                //设置文档的License信息->1.3 License information
                .termsOfServiceUrl("https://www.anseon.cn")
                .build();
    }
}