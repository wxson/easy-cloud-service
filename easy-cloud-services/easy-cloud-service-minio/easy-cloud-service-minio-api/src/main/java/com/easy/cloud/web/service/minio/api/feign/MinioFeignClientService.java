package com.easy.cloud.web.service.minio.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.MINIO_SERVICE)
public interface MinioFeignClientService {

}
