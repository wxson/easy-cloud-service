package com.easy.cloud.web.component.job.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Xxl-job Executor Properties
 *
 * @author GR
 * @date 2024/1/30 17:56
 */
@Data
@Validated
public class ExecutorProperties {

    /**
     * 默认端口
     * <p>
     * 这里使用 -1 表示随机
     */
    private static final Integer PORT_DEFAULT = -1;

    /**
     * 默认日志保留天数
     * <p>
     * 如果想永久保留，则设置为 -1
     */
    private static final Integer LOG_RETENTION_DAYS_DEFAULT = 30;

    /**
     * 应用名
     */
    @NotEmpty(message = "应用名不能为空")
    private String appName;
    /**
     * 执行器的 IP
     */
    private String ip;
    /**
     * 执行器的 Port
     */
    private Integer port = PORT_DEFAULT;
    /**
     * 日志地址
     */
    @NotEmpty(message = "日志地址不能为空")
    private String logPath;
    /**
     * 日志保留天数
     */
    private Integer logRetentionDays = LOG_RETENTION_DAYS_DEFAULT;
}
