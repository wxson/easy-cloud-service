package com.easy.cloud.web.service.member.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * MemberLevel请求数据
 *
 * @author Fast Java
 * @date 2024-02-18 11:46:58
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberLevelDTO {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 会会员等级
     */
    private Integer level;
    /**
     * 会员名称
     */
    private String name;
    /**
     * 会员经验边界，即超出该经验值自动升级
     */
    private Integer limitExperience;
}