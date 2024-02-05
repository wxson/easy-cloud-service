package com.easy.cloud.web.service.order.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 微信支付回调对象
 * <p>微信支付统一下单：https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/development/pay/order/unified.html</p>
 * <p>微信支付统一回调：https://developers.weixin.qq.com/miniprogram/dev/wxcloudrun/src/development/pay/callback/</p>
 *
 * @author Fast Java
 * @date 2021-11-12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class WxPayCallBackDTO {
    /**
     * 业务结果：SUCCESS
     */
    private String returnCode;
    /**
     * 应用ID
     */
    private String appid;
    /**
     * 商户ID
     */
    private String mchId;
    private String subAppid;
    private String subMchId;
    /**
     * 随机字符串，不长于32位
     */
    private String nonceStr;
    /**
     * 业务结果：SUCCESS/FAIL
     */
    private String resultCode;
    /**
     * openId
     */
    private String openid;
    /**
     * 是否订阅：公众号等
     */
    private String isSubscribe;
    private String subOpenid;
    private String subIsSubscribe;
    /**
     * 交易类型：JSAPI、NATIVE、APP
     */
    private String tradeType;
    /**
     * 银行类型，采用字符串类型的银行标识，银行类型见银行列表
     */
    private String bankType;
    /**
     * 订单总金额，单位为分
     */
    private String totalFee;
    /**
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    private String feeType;
    /**
     * 现金支付金额
     */
    private String cashFee;
    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 商家订单号
     */
    private String outTradeNo;
    /**
     * 支付完成时间
     */
    private String timeEnd;
}