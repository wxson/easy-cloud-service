package com.easy.cloud.web.service.pay.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;
import com.easy.cloud.web.service.pay.biz.service.IPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付代理
 *
 * @author GR
 * @date 2021-11-12 14:48
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "支付管理", tags = "支付管理")
public class PayController {

    private final IPayService payService;

    /**
     * 支付回调
     *
     * @param payDTO 支付参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.pay.biz.domain.vo.PayVO>
     */
    @PostMapping("callback/handler")
    @ApiOperation(value = "支付回调")
    public HttpResult<Object> payCallbackHandler(@RequestBody PayDTO payDTO) {
        log.info("接收到支付的订单回调通知：{}", payDTO);
        return HttpResult.ok(payService.payCallbackHandler(payDTO));
    }

    /**
     * 订单支付
     *
     * @param payDTO 支付参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.pay.biz.domain.vo.PayVO>
     */
    @PostMapping("order")
    @ApiOperation(value = "订单支付")
    public HttpResult<PayVO> payOrder(@RequestBody PayDTO payDTO) {
        return HttpResult.ok(payService.payOrder(payDTO));
    }

    /**
     * 商品支付
     *
     * @param payDTO 支付参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.pay.biz.domain.vo.PayVO>
     */
    @PostMapping("goods")
    @ApiOperation(value = "商品支付")
    public HttpResult<PayVO> payGoods(@RequestBody PayDTO payDTO) {
        return HttpResult.ok(payService.payGoods(payDTO));
    }

}
