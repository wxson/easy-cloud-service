package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.service.order.biz.service.IOrderPayService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Order APP API
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Slf4j
@RestController
@Tag(name = "订单支付管理", description = "订单支付管理")
public class OrderPayController {

    @Autowired
    private IOrderPayService orderPayService;

    @PostMapping("pay")
    @ApiOperation(value = "订单支付")
    public HttpResult<PayResponseBody> pay(@RequestBody @Validated PayRequestBody payRequestBody) {
        return HttpResult.ok(orderPayService.pay(payRequestBody));
    }
}