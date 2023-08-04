package com.easy.cloud.web.module.dict.configuration;

import com.easy.cloud.web.module.dict.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @author GR
 * @date 2021-3-9 17:59
 */
@Configuration
@ComponentScan({"com.easy.cloud.web.module.dict"})
public class DictAutoConfiguration {

  @Autowired
  private IDictService dictService;

  @Async
  @EventListener(ApplicationPreparedEvent.class)
  public void initDynamicRouteCache() {
    // 初始化字典配置
    dictService.init();
  }
}
