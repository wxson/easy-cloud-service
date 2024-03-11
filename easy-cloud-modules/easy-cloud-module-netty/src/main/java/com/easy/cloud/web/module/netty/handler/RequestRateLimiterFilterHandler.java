package com.easy.cloud.web.module.netty.handler;

import com.google.common.util.concurrent.RateLimiter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求限制
 *
 * @author GR
 * @date 2023/5/18 15:07
 */
@Slf4j
public class RequestRateLimiterFilterHandler extends ChannelInboundHandlerAdapter {

  /**
   * 全局限流器
   */
  private RateLimiter rateLimiter;

  public RequestRateLimiterFilterHandler(RateLimiter rateLimiter) {
    this.rateLimiter = rateLimiter;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    boolean tryAcquire = rateLimiter.tryAcquire();
    if (tryAcquire) {
      // 执行下一个Handle
      ctx.fireChannelRead(msg);
    } else {
      log.error("请求过于频繁,channelId:{},singleLimit:{},globalLimit:{}",
          ctx.channel().id().asShortText(), tryAcquire);
    }
  }
}
