package com.easy.cloud.web.service.charts.biz.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 潜力用户
 *
 * @author GR
 * @date 2021-12-26 16:36
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class PotentialUserVO {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String nickName;
    /**
     * 累计充值
     */
    private BigDecimal rechargeAmount;
}
