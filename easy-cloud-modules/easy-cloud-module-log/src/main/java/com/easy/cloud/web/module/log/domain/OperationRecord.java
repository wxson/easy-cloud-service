package com.easy.cloud.web.module.log.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 日志记录
 *
 * @author GR
 * @date 2021-8-4 14:10
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class OperationRecord {
    private String name;
    private String action;
    private String type;
    private String params;
    private String className;
    private String methodName;
    private Long elapsedTime;
    private String creatorAt;
    private LocalDateTime createAt;
}
