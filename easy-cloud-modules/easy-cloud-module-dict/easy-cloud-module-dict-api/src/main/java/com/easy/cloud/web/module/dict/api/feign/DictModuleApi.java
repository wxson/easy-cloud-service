package com.easy.cloud.web.module.dict.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.UPMS_SERVICE)
public interface DictModuleApi {
  
}
