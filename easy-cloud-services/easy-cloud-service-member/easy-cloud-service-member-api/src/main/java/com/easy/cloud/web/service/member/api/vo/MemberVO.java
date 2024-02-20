package com.easy.cloud.web.service.member.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Member展示数据
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * VIP 等级
     */
    private Integer vipLevel;
    /**
     * 积分
     */
    private Integer points;
    /**
     * 经验值
     */
    private Integer experience;
    /**
     * 总的充值
     */
    private BigDecimal totalRecharge;
    /**
     * 个性签名
     */
    private String profile;
}