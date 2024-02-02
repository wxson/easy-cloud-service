package com.easy.cloud.web.module.sms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.module.sms.api.dto.SmsValidateCodeDTO;
import com.easy.cloud.web.module.sms.biz.service.ISmsSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * SmsSend API
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Slf4j
@RestController
@RequestMapping(value = "sms_send")
@Api(value = "SmsSend", tags = "短信发送管理")
public class SmsSendController {

    @Autowired
    private ISmsSendService smsSendService;

    /**
     * 发送短信
     *
     * @param tel 短信接收电话
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @Inner
    @PostMapping("send/single/sms")
    @ApiOperation(value = "发送单条短信")
    public HttpResult<Boolean> sendSingleSms(@RequestParam @NotBlank(message = "当前电话不存在") String tel) {
        smsSendService.sendSms(tel);
        return HttpResult.ok(true);
    }

    /**
     * 短信验证码校验
     *
     * @param smsValidateCodeDTO 短信发送内容
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @Inner
    @PostMapping("validate/code")
    @ApiOperation(value = "短信验证码校验")
    public HttpResult<Boolean> validateSmsCode(@RequestBody SmsValidateCodeDTO smsValidateCodeDTO) {
        smsSendService.validateSmsCode(smsValidateCodeDTO);
        return HttpResult.ok(true);
    }
}