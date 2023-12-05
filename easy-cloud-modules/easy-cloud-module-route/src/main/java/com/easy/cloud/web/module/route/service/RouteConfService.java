package com.easy.cloud.web.module.route.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.module.route.domain.RouteConf;
import java.util.List;

/**
 * @author GR
 */
public interface RouteConfService extends IInitService {


  /**
   * 新增路由信息
   *
   * @param routeConf 路由信息
   * @return
   */
  boolean save(RouteConf routeConf);

  /**
   * 更新路由信息
   *
   * @param routeConf 路由信息
   * @return
   */
  boolean updateById(RouteConf routeConf);
  
  /**
   * 根据ID移除路由信息
   *
   * @param id 路由ID
   * @return
   */
  boolean removeById(String id);

  /**
   * 根据ID获取路由详情
   *
   * @param id 路由ID
   * @return
   */
  RouteConf getById(String id);

  /**
   * 查询所有路由信息
   *
   * @return
   */
  List<RouteConf> list();

  /**
   * 发送路有变化通知
   *
   * @param routeConf 变化的路由信息
   */
  void sendRouteConfChangeNotice(RouteConf routeConf);
}
