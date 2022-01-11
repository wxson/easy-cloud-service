package com.easy.cloud.web.service.pay.biz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GR
 * @date 2021-11-12 17:18
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class PrePayBody {
    /**
     * app_id
     */
    private String appid;
    /**
     * 商家号
     */
    private String mch_id;
    /**
     * 订单名称
     */
    private String body;
    /**
     * 总金额
     */
    private Integer total_fee;
    /**
     * 随机号
     */
    private String nonce_str;
    /**
     * 设备号
     */
    private String device_info;
    /**
     * 签名
     */
    private String sign;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 终端IP,支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
     */
    private String spbill_create_ip;
    /**
     * 通知地址,接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。公网域名必须为https，如果是走专线接入，使用专线NAT IP或者私有回调域名可使用http。
     */
    private String notify_url;
    /**
     * 交易类型
     */
    private String trade_type;
}
