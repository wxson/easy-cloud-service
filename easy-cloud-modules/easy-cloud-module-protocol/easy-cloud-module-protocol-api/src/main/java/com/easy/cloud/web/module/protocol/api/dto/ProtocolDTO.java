package com.easy.cloud.web.module.protocol.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Protocol 请求数据
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolDTO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 应用唯一标识
     */
    private String uniqueId;

    /**
     * 协议名称
     */
    private String name;
    /**
     * 协议内容
     */
    private String content;
    /**
     * 描述
     */
    private String description;
    /**
     * 备注
     */
    private String remark;
}