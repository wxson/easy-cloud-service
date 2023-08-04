package com.easy.cloud.web.module.dict.domain.vo;

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
    private String name;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private Integer dictId;
    /**
     *
     */
    private String describe;
    /**
     *
     */
    private String value;
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
    /**
     * 更新用户
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateAt;
}