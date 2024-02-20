package com.easy.cloud.web.service.member.api.dto;

import com.easy.cloud.web.service.member.api.enums.ExperienceBizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * MemberExperienceRecord请求数据
 *
 * @author Fast Java
 * @date 2024-02-18 16:52:27
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberExperienceRecordDTO {

    /**
     * 文档ID
     */
    private String id;
    /**
     * 用户
     */
    private String userId;
    /**
     * 上一经验值
     */
    private Integer lastExperience;
    /**
     * 当前经验值
     */
    private Integer currentExperience;
    /**
     * 经验值业务类型
     */
    private ExperienceBizTypeEnum experienceBizType;
    /**
     * 经验增加值：正数为增加，负数为扣减
     */
    private Integer offsetExperience;
    /**
     * 备注
     */
    private String remark;
}