package com.easy.cloud.web.service.upms.biz.configuration;

import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @author GR
 * @date 2021-3-9 17:59
 */
@Slf4j
@Configuration
@RefreshScope
@AllArgsConstructor
public class UpmsAutoConfiguration {

    private final IMenuService menuService;

    private final IRoleService roleService;

    @Async
    @EventListener(ApplicationPreparedEvent.class)
    public void initDynamicRouteCache() {
        // 初始化菜单配置
        menuService.initDefaultConfiguration();
        // 初始化角色配置
        roleService.initDefaultConfiguration();
    }
}
