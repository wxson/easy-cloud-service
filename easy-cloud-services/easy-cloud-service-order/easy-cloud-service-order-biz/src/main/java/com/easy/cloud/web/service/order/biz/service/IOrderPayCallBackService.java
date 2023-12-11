package com.easy.cloud.web.service.order.biz.service;

import com.easy.cloud.web.service.order.api.domain.dto.AliPayCallBackDTO;
import com.easy.cloud.web.service.order.api.domain.dto.WxPayCallBackDTO;

/**
 * @author GR
 * @date 2021-11-27 10:05
 */
public interface IOrderPayCallBackService {

  /**
   * 微信支付回调通知
   *
   * @param wxPayCallBackDTO 通知内容
   */
  void wxPayCallBack(WxPayCallBackDTO wxPayCallBackDTO);

  /**
   * 支付宝支付回调通知
   *
   * @param aliPayCallBackDTO 通知内容
   */
  void aliPayCallBack(AliPayCallBackDTO aliPayCallBackDTO);
}
