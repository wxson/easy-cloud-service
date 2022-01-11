package com.easy.cloud.web.module.sms.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.sms.domain.dto.SmsDTO;
import com.easy.cloud.web.module.sms.service.ISmsSendRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信管理
 *
 * @author GR
 * @date 2021-12-9 19:08
 */
@Slf4j
@RestController
@RequestMapping("sms")
@AllArgsConstructor
@Api(value = "sms", tags = "短信管理模块")
public class SmsController {

    private final ISmsSendRecordService smsSendRecordService;

    /**
     * 短信回调
     *
     * @param smsDTO 绑定手机号
     * @return void
     */
    @PostMapping("call/back")
    @ApiOperation(value = "短信回调")
    public HttpResult<Boolean> callBack(@RequestBody SmsDTO smsDTO) {
        return HttpResult.ok(smsSendRecordService.callBack(smsDTO));
    }

    /**
     * 绑定手机号
     *
     * @param smsDTO 绑定手机号
     * @return void
     */
    @PostMapping("bind")
    @ApiOperation(value = "绑定手机号")
    public HttpResult<Boolean> bindTel(@RequestBody SmsDTO smsDTO) {
        return HttpResult.ok(smsSendRecordService.bindTel(smsDTO));
    }

    /**
     * 获取手机验证码
     *
     * @param smsDTO 验证手机验证码
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("code")
    @ApiOperation(value = "获取手机验证码")
    public HttpResult<Boolean> getCodeByTel(@RequestBody SmsDTO smsDTO) {
        return HttpResult.ok(smsSendRecordService.getCodeByTel(smsDTO));
    }

}
