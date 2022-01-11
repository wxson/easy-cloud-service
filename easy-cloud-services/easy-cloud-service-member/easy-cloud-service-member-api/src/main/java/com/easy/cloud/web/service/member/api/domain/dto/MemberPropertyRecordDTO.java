package com.easy.cloud.web.service.member.api.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员资产信息表
 *
 * @author GR
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class MemberPropertyRecordDTO {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 资产来源
     */
    private Integer origin;

    /**
     * 最晚创建时间
     */
    private String maxCreateAt;

    /**
     * 最早创建时间
     */
    private String minCreateAt;


}