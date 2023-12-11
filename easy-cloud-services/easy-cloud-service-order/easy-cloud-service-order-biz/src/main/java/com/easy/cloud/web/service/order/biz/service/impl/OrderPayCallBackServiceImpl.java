package com.easy.cloud.web.service.order.biz.service.impl;

import com.easy.cloud.web.service.order.api.domain.dto.AliPayCallBackDTO;
import com.easy.cloud.web.service.order.api.domain.dto.WxPayCallBackDTO;
import com.easy.cloud.web.service.order.biz.service.IOrderPayCallBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单回调逻辑
 *
 * @author GR
 * @date 2021-11-27 10:13
 */
@Slf4j
@Service
public class OrderPayCallBackServiceImpl implements IOrderPayCallBackService {

  @Override
  public void wxPayCallBack(WxPayCallBackDTO wxPayCallBackDTO) {

  }

  @Override
  public void aliPayCallBack(AliPayCallBackDTO aliPayCallBackDTO) {

  }
}
