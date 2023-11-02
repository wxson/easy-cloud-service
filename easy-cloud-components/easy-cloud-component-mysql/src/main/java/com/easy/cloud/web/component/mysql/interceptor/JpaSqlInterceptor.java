package com.easy.cloud.web.component.mysql.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

/**
 * JPA Sql拦截器
 * <p>1、实现逻辑查询</p>
 * <p>2、实现渠道查询</p>
 *
 * @author GR
 * @date 2023/10/31 11:47
 */
@Slf4j
@Component
public class JpaSqlInterceptor implements StatementInspector {

  @Override
  public String inspect(String s) {
//    log.info("JpaSqlInterceptor:{}", s);
    return s;
  }
}
