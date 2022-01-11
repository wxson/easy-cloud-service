package com.easy.cloud.web.service.member.api.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author GR
 * @date 2021-11-28 18:05
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class MemberDTO {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 资产来源
     */
    private Integer origin;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 累计充值
     */
    private BigDecimal totalRecharge;
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
}
