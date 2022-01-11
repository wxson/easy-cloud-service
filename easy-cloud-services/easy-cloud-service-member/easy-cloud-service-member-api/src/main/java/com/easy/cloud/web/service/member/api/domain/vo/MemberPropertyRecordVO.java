package com.easy.cloud.web.service.member.api.domain.vo;

import com.easy.cloud.web.component.core.service.IConvertProxy;
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
public class MemberPropertyRecordVO implements IConvertProxy {
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
     * 金币
     */
    private Long amount;

    /**
     * 钻石
     */
    private Long diamond;

    /**
     * 点券
     */
    private Long coupon;

    /**
     * 创建用户
     */
    private String creatorAt;

    /**
     * 创建时间
     */
    private String createAt;
}