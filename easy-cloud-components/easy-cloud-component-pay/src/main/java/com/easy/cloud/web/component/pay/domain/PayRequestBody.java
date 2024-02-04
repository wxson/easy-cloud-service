package com.easy.cloud.web.component.pay.domain;

import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付请求数据
 *
 * @author GR
 * @date 2024/2/4 15:55
 */
@Data
public abstract class PayRequestBody {

    /**
     * 支付类型
     */
    private PayTypeEnum payType;

    /**
     * 订单编码
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 支付金额
     */
    private BigDecimal amount;
}
