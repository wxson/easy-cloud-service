package com.easy.cloud.web.component.core.tenant;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


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
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String tenantId = request.getHeader(GlobalCommonConstants.TENANT_ID_FIELD);
    log.debug("获取header中的租户ID为:{}", tenantId);

    if (StrUtil.isNotBlank(tenantId)) {
      TenantContextHolder.setTenantId(tenantId);
    } else {
      TenantContextHolder.setTenantId(GlobalCommonConstants.DEFAULT_TENANT);
    }

    filterChain.doFilter(request, response);
    TenantContextHolder.clear();
  }

}
