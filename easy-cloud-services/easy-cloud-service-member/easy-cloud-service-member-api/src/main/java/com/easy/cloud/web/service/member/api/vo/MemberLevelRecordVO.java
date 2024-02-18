package com.easy.cloud.web.service.member.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * MemberLevelRecord展示数据
 *
 * @author Fast Java
 * @date 2024-02-18 16:42:58
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberLevelRecordVO {

    /**
     * 文档ID
     */
    private Long id;
    /**
     * 用户
     */
    private String userId;
    /**
     * 上一等级ID
     */
    private String lastLevelId;
    /**
     * 上一等级名称
     */
    private Integer lastLevelName;
    /**
     * 上一等级
     */
    private Integer lastLevel;
    /**
     * 当前等级ID
     */
    private String currentLevelId;
    /**
     * 当前等级名称
     */
    private Integer currentLevelName;
    /**
     * 当前等级
     */
    private Integer currentLevel;
    /**
     * 当前经验值
     */
    private Integer currentExperience;
    /**
     * 备注
     */
    private String remark;
}