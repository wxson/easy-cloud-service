package com.easy.cloud.web.module.dict.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * DictItem展示数据
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DictItemVO {
    /**
     * 文档ID
     */
    private Long id;
    /**
     *
     */
    private String label;
    /**
     *
     */
    private String value;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private String dictType;
    /**
     * 创建用户
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createAt;
}