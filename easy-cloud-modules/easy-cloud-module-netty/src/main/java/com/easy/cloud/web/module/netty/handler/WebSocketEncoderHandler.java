package com.easy.cloud.web.module.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * WebSocket 编码器
 *
 * @author GR
 * @date 2023/5/18 15:40
 */
public class WebSocketEncoderHandler extends ChannelOutboundHandlerAdapter {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    super.write(ctx, msg, promise);
  }
}
