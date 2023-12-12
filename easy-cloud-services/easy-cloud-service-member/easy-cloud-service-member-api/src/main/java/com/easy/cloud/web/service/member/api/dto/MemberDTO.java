package com.easy.cloud.web.service.member.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Member请求数据
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    /**
     * 文档ID
     */
    private Long id;
    /**
     *
     */
    private Integer vipLevel;
    /**
     *
     */
    private Integer amount;
    /**
     *
     */
    private Integer diamond;
    /**
     *
     */
    private Integer coupon;
    /**
     *
     */
    private Integer level;
    /**
     *
     */
    private String ip;
    /**
     *
     */
    private String profile;
    /**
     *
     */
    private Integer experience;
    /**
     *
     */
    private Integer totalRecharge;
    /**
     *
     */
    private String userId;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
}