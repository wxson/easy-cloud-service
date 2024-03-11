package com.easy.cloud.web.module.netty.configuration;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.module.netty.annotation.Action;
import com.easy.cloud.web.module.netty.annotation.ActionEndpoint;
import com.easy.cloud.web.module.netty.annotation.OnConnect;
import com.easy.cloud.web.module.netty.annotation.OnDisconnect;
import com.easy.cloud.web.module.netty.common.NettyConstants;
import com.easy.cloud.web.module.netty.domain.ChannelRequest;
import com.easy.cloud.web.module.netty.domain.ChannelResponse;
import com.easy.cloud.web.module.netty.proxy.ActionEndpointProxy;
import com.easy.cloud.web.module.netty.utils.ActionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 通信管理
 *
 * @author GR
 * @date 2023/5/18 16:22
 */
@Slf4j
@Component
public class ActionEndpointManager implements ApplicationContextAware {

  /**
   * 路由映射
   */
  private Map<String, ActionEndpointProxy> actionMethods = new HashMap<>();

  /**
   * 从Sring的容器中读取GameHandlerComponent标记的实例
   *
   * @param context
   */
  private void scanActionEndpoint(ApplicationContext context) {
    String[] handlerBeanNames = context.getBeanNamesForAnnotation(ActionEndpoint.class);
    for (String beanName : handlerBeanNames) {
      // 获取实例对象
      Object beanObj = context.getBean(beanName);
      // 获取类注解
      ActionEndpoint actionEndpoint = beanObj.getClass().getAnnotation(ActionEndpoint.class);
      //查找处理请求的方法
      Method[] methods = beanObj.getClass().getMethods();
      for (Method method : methods) {
        this.registerConnectEvent(method, beanObj);
        this.registerDisconnectEvent(method, beanObj);
        this.registerActionEvent(method, beanObj, actionEndpoint);
      }
    }
  }

  /**
   * 注册连接事件
   *
   * @param method
   * @param beanObj
   */
  private void registerConnectEvent(Method method, Object beanObj) {
    OnConnect onConnect = method.getAnnotation(OnConnect.class);
    if (Objects.nonNull(onConnect)) {
      actionMethods.put(NettyConstants.CHANNEL_CONNECT_KEY,
          new ActionEndpointProxy(beanObj, method, method.getParameterTypes()));
    }
  }

  /**
   * 注册断开事件
   *
   * @param method
   * @param beanObj
   */
  private void registerDisconnectEvent(Method method, Object beanObj) {
    OnDisconnect onDisconnect = method.getAnnotation(OnDisconnect.class);
    if (Objects.nonNull(onDisconnect)) {
      actionMethods.put(NettyConstants.CHANNEL_DISCONNECT_KEY,
          new ActionEndpointProxy(beanObj, method, method.getParameterTypes()));
    }
  }

  /**
   * 注册事件
   *
   * @param method
   * @param beanObj
   * @param actionEndpoint
   */
  private void registerActionEvent(Method method, Object beanObj, ActionEndpoint actionEndpoint) {
    Action action = method.getAnnotation(Action.class);
    if (Objects.nonNull(action) && StringUtils.isNoneBlank(action.value())) {
      String basePathPrefix = actionEndpoint.value();
      String path = action.value();
      String uniqueActionPath = ActionUtil.getUniqueActionPath(basePathPrefix, path);
      // 是否存在重复数据
      ActionEndpointProxy actionEndpointProxy = actionMethods.get(uniqueActionPath);
      if (Objects.nonNull(actionEndpointProxy)) {
        String format = String.format("duplicate definition action：%s", uniqueActionPath);
        log.error(format);
        throw new BusinessException(format);
      }
      actionMethods.put(uniqueActionPath,
          new ActionEndpointProxy(beanObj, method, method.getParameterTypes()));
    }
  }

  /**
   * 方法调用
   *
   * @param channelRequest
   * @param channelResponse
   */
  public Object invoke(ChannelRequest channelRequest, ChannelResponse channelResponse)
      throws InvocationTargetException, IllegalAccessException {
    if (actionMethods.containsKey(channelRequest.getAction())) {
      return actionMethods.get(channelRequest.getAction()).invoke(channelRequest, channelResponse);
    } else {
      throw new BusinessException(404, "No match was found action");
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.scanActionEndpoint(applicationContext);
  }
}
