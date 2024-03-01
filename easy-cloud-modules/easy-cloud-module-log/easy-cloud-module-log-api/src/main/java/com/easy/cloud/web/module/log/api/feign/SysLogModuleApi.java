package com.easy.cloud.web.module.log.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.log.api.dto.SysLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(contextId = "sysLogModuleApi", value = ServiceNameConstants.UPMS_SERVICE)
public interface SysLogModuleApi {

    /**
     * 保存日志
     *
     * @param sysLogDTO 日志实体
     * @return succes、false
     */
    @PostMapping("/sys/log/save")
    HttpResult<Boolean> saveLog(@RequestBody SysLogDTO sysLogDTO);
}
