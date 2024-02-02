package com.easy.cloud.web.module.sms.biz.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.sms.biz.service.ISmsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * SmsSend API
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Slf4j
@RestController
@RequestMapping(value = "sms_callback")
public class SmsCallBackController {

    @Autowired
    private ISmsService smsService;

    /**
     * 阿里云短信回调
     *
     * @param request 回调数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("/aliyun")
    @Operation(summary = "阿里云短信的回调", description = "参见 https://help.aliyun.com/document_detail/120998.html 文档")
    public HttpResult<Boolean> receiveAliyunSmsStatus(HttpServletRequest request) throws Throwable {
        // 回去回执信息
        String responseText = ServletUtil.getBody(request);
        // TODO 可做为短信发送成功的标志
        log.info("接收到阿里云短信回执信息：{}", responseText);
        return HttpResult.ok(true);
    }

    /**
     * 腾讯云回调
     *
     * @param request 回调数据
     * @return CommonResult<Boolean>
     */
    @PostMapping("/tencent")
    @Operation(summary = "腾讯云短信的回调", description = "参见 https://cloud.tencent.com/document/product/382/52077 文档")
    public HttpResult<Boolean> receiveTencentSmsStatus(HttpServletRequest request) throws Throwable {
        // 回去回执信息
        String responseText = ServletUtil.getBody(request);
        // TODO 可做为短信发送成功的标志
        log.info("接收到腾讯云短信回执信息：{}", responseText);
        return HttpResult.ok(true);
    }
}