package com.easy.cloud.web.module.netty.domain;

import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求
 *
 * @author GR
 * @date 2023/5/18 16:42
 */
@Getter
@Setter
public class ChannelRequest {

  /**
   * 上下文
   */
  private ChannelHandlerContext channelHandlerContext;

  public ChannelRequest() {
  }

  public ChannelRequest(ChannelHandlerContext channelHandlerContext) {
    this.channelHandlerContext = channelHandlerContext;
  }

  /**
   * 方法
   */
  private String action;

  /**
   * 请求参数
   */
  private Map<String, List<String>> urlParams;

  /**
   * 请求头
   */
  private Object body;

  public String getSingleUrlParam(String name) {
    List<String> values = urlParams.get(name);
    if (values != null && values.size() == 1) {
      return values.iterator().next();
    }
    return null;
  }
}
