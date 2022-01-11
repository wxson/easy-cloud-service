package com.easy.cloud.web.component.core.tenant;

import com.easy.cloud.web.component.core.constants.GlobalConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 类工具
 *
 * @author GR
 * @date 2021-4-7 10:19
 */
@Slf4j
public class FeignTenantInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (TenantContextHolder.getTenantId() == null) {
            log.debug("TTL 中的 租户ID为空，feign拦截器 >> 跳过");
            return;
        }
        requestTemplate.header(GlobalConstants.TENANT_ID, TenantContextHolder.getTenantId().toString());
    }

}
