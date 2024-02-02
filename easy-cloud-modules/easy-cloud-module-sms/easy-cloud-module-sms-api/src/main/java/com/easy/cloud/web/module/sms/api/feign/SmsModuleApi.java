package com.easy.cloud.web.module.sms.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.sms.api.dto.SmsValidateCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(contextId = "smsModuleApi", value = ServiceNameConstants.UPMS_SERVICE)
public interface SmsModuleApi {

    /**
     * 短信验证嘛验证
     *
     * @param smsValidateCodeDTO
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
     */
    @PostMapping("sms_send/validate/code")
    HttpResult<Boolean> validateSmsCode(@RequestBody SmsValidateCodeDTO smsValidateCodeDTO);
}
