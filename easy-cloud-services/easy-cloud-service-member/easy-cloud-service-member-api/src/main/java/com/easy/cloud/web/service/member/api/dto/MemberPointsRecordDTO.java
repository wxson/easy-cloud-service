package com.easy.cloud.web.service.member.api.dto;

import com.easy.cloud.web.service.member.api.enums.PointsBizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * MemberPointsRecord请求数据
 *
 * @author Fast Java
 * @date 2024-02-18 16:54:49
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointsRecordDTO {

    /**
     * 当前用户
     */
    private String userId;
    /**
     * 上一积分
     */
    private Integer lastPoints;
    /**
     * 当前积分
     */
    private Integer currentPoints;
    /**
     * 积分业务类型
     */
    private PointsBizTypeEnum pointsBizType;
    /**
     * 积分增加值：正数为增加，负数为扣减
     */
    private Integer offsetPoints;
    /**
     * 备注
     */
    private String remark;
}