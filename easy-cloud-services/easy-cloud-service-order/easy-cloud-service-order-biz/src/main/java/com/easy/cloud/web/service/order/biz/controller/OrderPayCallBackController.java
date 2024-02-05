package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
import com.easy.cloud.web.service.order.api.dto.AliPayCallBackDTO;
import com.easy.cloud.web.service.order.api.dto.WxPayCallBackDTO;
import com.easy.cloud.web.service.order.biz.annotation.PayCallBackSignature;
import com.easy.cloud.web.service.order.biz.service.IOrderPayCallBackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单支付回调接口
 *
 * @author Fast Java
 * @date 2021-11-12
 */
@Slf4j
@RestController
@AllArgsConstructor
public class OrderPayCallBackController {

    private final IOrderPayCallBackService orderPayCallBackService;

    /**
     * 微信支付回调接口
     *
     * @param wxPayCallBackDTO 回调参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("wx/pay/call/back")
    @PayCallBackSignature(payType = PayTypeEnum.WX_PAY)
    public HttpResult<Boolean> wxOrderPayCallBack(@RequestBody WxPayCallBackDTO wxPayCallBackDTO) {
        orderPayCallBackService.wxPayCallBack(wxPayCallBackDTO);
        // 必须返回SUCCESS，否则可能会出现问题
        return HttpResult.ok("SUCCESS", "成功", true);
    }

    /**
     * 支付宝支付回调接口
     *
     * @param aliPayCallBackDTO 请求参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("ali/pay/call/back")
    @PayCallBackSignature(payType = PayTypeEnum.ALI_PAY)
    public HttpResult<Boolean> aliOrderPayCallBack(@RequestBody AliPayCallBackDTO aliPayCallBackDTO) {
        // 必须返回SUCCESS，否则可能会出现问题
        return HttpResult.ok("SUCCESS", "成功", true);
    }
}