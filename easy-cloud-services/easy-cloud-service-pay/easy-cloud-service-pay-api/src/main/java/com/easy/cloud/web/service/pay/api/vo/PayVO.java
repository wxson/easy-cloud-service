package com.easy.cloud.web.service.pay.api.vo;

import com.easy.cloud.web.service.order.api.enums.PayStatusEnum;
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
public class PayVO {

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
