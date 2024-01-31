package com.easy.cloud.web.component.job.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Xxl-job 属性配置
 *
 * @author GR
 * @date 2024/1/30 17:00
 */
@Data
@Validated
@ConfigurationProperties("xxl.job")
public class XxlJobProperties {

    /**
     * 是否开启，默认为 true 关闭
     */
    private Boolean enabled = true;
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 调度器地址
     */
    @NotEmpty(message = "调度器地址不能为空")
    private String addresses;
    /**
     * 执行器配置
     */
    @NotNull(message = "执行器配置不能为空")
    private ExecutorProperties executor;

}
