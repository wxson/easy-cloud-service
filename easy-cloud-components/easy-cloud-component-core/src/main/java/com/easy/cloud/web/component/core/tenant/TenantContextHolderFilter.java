package com.easy.cloud.web.component.core.tenant;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 传递 RestTemplate 请求的租户ID
 *
 * @author GR
 * @date 2021-4-7 10:19
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String tenantId = request.getHeader(GlobalConstants.TENANT_ID);
        log.debug("获取header中的租户ID为:{}", tenantId);

        if (StrUtil.isNotBlank(tenantId)) {
            TenantContextHolder.setTenantId(Integer.parseInt(tenantId));
        } else {
            TenantContextHolder.setTenantId(GlobalConstants.DEFAULT_TENANT_ID);
        }

        filterChain.doFilter(request, response);
        TenantContextHolder.clear();
    }

}
