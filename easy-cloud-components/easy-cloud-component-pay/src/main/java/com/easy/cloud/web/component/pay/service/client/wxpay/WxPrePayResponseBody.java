package com.easy.cloud.web.component.pay.service.client.wxpay;

import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.component.pay.enums.PayStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GR
 * @date 2024/2/4 16:53
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class WxPrePayResponseBody extends PayResponseBody {

    /**
     * 支付状态
     */
    private PayStatusEnum payStatus;
    /**
     * APP ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String partnerId;
    /**
     * 预支付ID
     */
    private String prepayId;
    /**
     * 扩展字段,默认：Sign=WXPay
     */
    private String packageValue;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 签名
     */
    private String sign;
}
