package com.easy.cloud.web.module.sms.api.feign;

import com.easy.cloud.web.component.core.constants.SecurityConstants;
import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.sms.api.dto.SmsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(contextId = "smsModuleApi", value = ServiceNameConstants.UPMS_SERVICE)
public interface SmsModuleApi {

  /**
   * 保存日志
   *
   * @param smsDTO 短信类容
   * @param from   是否内部调用
   * @return succes、false
   */
  @PostMapping("/sms/save")
  HttpResult<Boolean> saveSms(@RequestBody SmsDTO smsDTO,
      @RequestHeader(SecurityConstants.ORIGIN) String from);
}
