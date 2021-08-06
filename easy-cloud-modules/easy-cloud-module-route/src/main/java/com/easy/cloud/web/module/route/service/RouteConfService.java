package com.easy.cloud.web.module.route.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.cloud.web.module.route.domain.RouteConf;

/**
 * @author GR
 */
public interface RouteConfService extends IService<RouteConf> {

    /**
     * 发送路有变化通知
     *
     * @param routeConf 变化的路由信息
     */
    void sendRouteConfChangeNotice(RouteConf routeConf);

}
