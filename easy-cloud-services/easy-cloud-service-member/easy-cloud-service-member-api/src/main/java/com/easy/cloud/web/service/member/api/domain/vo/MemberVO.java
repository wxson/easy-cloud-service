package com.easy.cloud.web.service.member.api.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author GR
 * @date 2021-11-28 18:05
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class MemberVO {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 累计充值
     */
    private BigDecimal totalRecharge;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 金币
     */
    private Long amount;

    /**
     * 当前的总充值钻石数
     */
    private Long currentRechargeTotalDiamondNum;

    /**
     * 下一VIP钻石钻石数
     */
    private Long nextVipLevelDiamondNum;

    /**
     * 钻石
     */
    private Long diamond;

    /**
     * 点券
     */
    private Long coupon;

    /**
     * 头像框
     */
    private String avatarFrame;

    /**
     * 胜率
     */
    private Float winRate;

    /**
     * 总对局
     */
    private Integer totalPlayerCount;

    /**
     * 段位
     */
    private Integer danGrading;

    /**
     * 段位名称
     */
    private Integer danGradingName;

    /**
     * 专属魔法表情
     */
    private List<Integer> magicFaceList;

}
