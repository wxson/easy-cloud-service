package com.easy.cloud.web.service.pay.api.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 支付返回信息
 *
 * @author GR
 * @date 2021-11-12 14:44
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class PayDTO {
    /**
     * 订单唯一编号
     */
    private String orderNo;
    /**
     * 支付方式:1 微信  2 支付宝
     */
    private Integer payType;
}
