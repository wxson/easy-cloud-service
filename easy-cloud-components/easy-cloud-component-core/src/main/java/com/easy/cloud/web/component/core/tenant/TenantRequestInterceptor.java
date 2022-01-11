package com.easy.cloud.web.component.core.tenant;

import com.easy.cloud.web.component.core.constants.GlobalConstants;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 传递 RestTemplate 请求的租户ID
 *
 * @author GR
 * @date 2021-4-7 10:19
 */
public class TenantRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        if (TenantContextHolder.getTenantId() != null) {
            request.getHeaders().set(GlobalConstants.TENANT_ID, String.valueOf(TenantContextHolder.getTenantId()));
        }

        return execution.execute(request, body);
    }

}
