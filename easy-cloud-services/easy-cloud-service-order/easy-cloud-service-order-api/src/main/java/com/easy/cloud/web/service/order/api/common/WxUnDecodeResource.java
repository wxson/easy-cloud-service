package com.easy.cloud.web.service.order.api.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 参数参考 https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_5.shtml
 *
 * @author GR
 * @date 2021-11-27 11:33
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class WxUnDecodeResource {

  /**
   * 应用ID
   */
  private String appid;
  /**
   * 商户号
   */
  private String mchid;
  /**
   * 商户订单号
   */
  private String out_trade_no;
  /**
   * 微信支付订单号
   */
  private String transaction_id;
  /**
   * 交易类型
   */
  private String trade_type;
  /**
   * 交易状态
   */
  private String trade_state;
  /**
   * 交易状态描述
   */
  private String trade_state_desc;
  /**
   * 付款银行
   */
  private String bank_type;
  /**
   * 附加数据
   */
  private String attach;
  /**
   * 支付完成时间
   */
  private String success_time;
  /**
   * 支付人
   */
  private Payer payer;
  /**
   * 金额
   */
  private Amount amount;
  /**
   * 场景信息
   */
  private SceneInfo scene_info;
  /**
   * 优惠详情
   */
  private PromotionDetail promotion_detail;

  @Data
  @Accessors(chain = true)
  @NoArgsConstructor(staticName = "build")
  public static class Payer {

    /**
     * 用户标识
     */
    private String openid;
  }

  @Data
  @Accessors(chain = true)
  @NoArgsConstructor(staticName = "build")
  public static class Amount {

    /**
     * 总金额
     */
    private Integer total;
    /**
     * 总金额
     */
    private Integer payer_total;
    /**
     * 货币类型
     */
    private String currency;
    /**
     * 用户支付币种
     */
    private String payer_currency;
  }

  @Data
  @Accessors(chain = true)
  @NoArgsConstructor(staticName = "build")
  public static class SceneInfo {

    /**
     * 商户端设备号
     */
    private String device_id;
  }

  @Data
  @Accessors(chain = true)
  @NoArgsConstructor(staticName = "build")
  public static class PromotionDetail {

    /**
     * 券ID
     */
    private String coupon_id;
    /**
     * 优惠名称
     */
    private String name;
    /**
     * 优惠范围
     */
    private String scope;
    /**
     * 优惠类型
     */
    private String type;
    /**
     * 优惠券面额
     */
    private Integer amount;
    /**
     * 活动
     */
    private String stock_id;
  }
}
