package com.easy.cloud.web.module.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 请求确认，过滤掉不符合要求的数据
 *
 * @author GR
 * @date 2023/5/18 15:12
 */
public class RequestConfirmHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    // 执行下一个Handle
    ctx.fireChannelRead(msg);
  }
}
