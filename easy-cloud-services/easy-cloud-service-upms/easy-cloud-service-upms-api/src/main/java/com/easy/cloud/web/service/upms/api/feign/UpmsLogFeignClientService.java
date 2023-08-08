package com.easy.cloud.web.service.upms.api.feign;

import com.easy.cloud.web.component.core.constants.SecurityConstants;
import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.upms.api.dto.OperationLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(contextId = "upmsLogFeignClientService", value = ServiceNameConstants.UPMS_SERVICE)
public interface UpmsLogFeignClientService {

  /**
   * 保存日志
   *
   * @param operationLogDTO 日志实体
   * @param from            是否内部调用
   * @return succes、false
   */
  @PostMapping("/log/save")
  HttpResult<Boolean> saveLog(@RequestBody OperationLogDTO operationLogDTO,
      @RequestHeader(SecurityConstants.ORIGIN) String from);
}
