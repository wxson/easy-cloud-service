package com.easy.cloud.web.module.netty.domain;

import cn.hutool.core.collection.CollUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

/**
 * 响应
 *
 * @author GR
 * @date 2023/5/18 16:42
 */
@Setter
@Getter
public class ChannelResponse {

  /**
   * 上下文
   */
  private ChannelHandlerContext channelHandlerContext;

  private Channel channel;

  public ChannelResponse(ChannelHandlerContext channelHandlerContext) {
    this.channelHandlerContext = channelHandlerContext;
    this.channel = channelHandlerContext.channel();
  }

  /**
   * 发送消息，针对单个链接
   *
   * @param message
   */
  public void sendMessage(Object message) {
    this.sendMessage(channelHandlerContext.channel(), message);
  }

  /**
   * 发送消息，针对特定用户
   *
   * @param message
   */
  public void sendMessage(Object message, Collection<Channel> channels) {
    if (CollUtil.isNotEmpty(channels)) {
      channels.forEach(channel -> this.sendMessage(channel, message));
    }
  }

  /**
   * 发送世界广播
   *
   * @param message
   */
  public void sendGlobalBroadcast(Object message) {
    this.sendMessage(message, NettyContext.getInstance().getAllChannels());
  }

  /**
   * 发送信息
   *
   * @param channel
   * @param message
   */
  public void sendMessage(Channel channel, Object message) {
    ChannelBroadcast.sendBroadcast(channel, message);
  }
}
