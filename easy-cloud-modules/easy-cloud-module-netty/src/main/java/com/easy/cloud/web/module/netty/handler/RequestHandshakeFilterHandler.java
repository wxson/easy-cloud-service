package com.easy.cloud.web.module.netty.handler;

import com.easy.cloud.web.module.netty.common.NettyConstants;
import com.easy.cloud.web.module.netty.configuration.ActionEndpointManager;
import com.easy.cloud.web.module.netty.domain.ChannelRequest;
import com.easy.cloud.web.module.netty.domain.ChannelResponse;
import com.easy.cloud.web.module.netty.domain.NettyContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求握手过滤
 *
 * @author GR
 * @date 2023/5/18 15:07
 */
@Slf4j
public class RequestHandshakeFilterHandler extends ChannelInboundHandlerAdapter {

  private final ActionEndpointManager actionEndpointManager;

  public RequestHandshakeFilterHandler(ActionEndpointManager actionEndpointManager) {
    this.actionEndpointManager = actionEndpointManager;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    // 握手完成，表示连接成功
    if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
      // 握手信息
      HandshakeComplete handshakeComplete = (HandshakeComplete) evt;
      QueryStringDecoder queryDecoder = new QueryStringDecoder(handshakeComplete.requestUri());
      // 获取channel
      Channel channel = ctx.channel();
      // 获取全局唯一字段
      String longText = channel.id().asLongText();
      // 断开通知
      channel.closeFuture().addListener(future -> {
        // 构建断开连接的请求数据
        ChannelRequest channelRequest = this
            .buildChannelRequest(ctx, NettyConstants.CHANNEL_DISCONNECT_KEY);
        // 设置URL参数
        channelRequest.setUrlParams(queryDecoder.parameters());
        // 调用断开连接方法
        actionEndpointManager.invoke(channelRequest, new ChannelResponse(ctx));
        // 移除全局Channel
//        NettyContext.getInstance().removeChannel(longText);
      });
      // 不包含则通知新的链接
      if (!NettyContext.getInstance().getAllChannelMap().containsKey(longText)) {
        // 添加Channel到上下文
//        NettyContext.getInstance().addChannel(longText, channel);
        // 构建请求数据
        ChannelRequest channelRequest = this
            .buildChannelRequest(ctx, NettyConstants.CHANNEL_CONNECT_KEY);
        // 设置URL参数
        channelRequest.setUrlParams(queryDecoder.parameters());
        // 调用连接方法
        actionEndpointManager.invoke(channelRequest, new ChannelResponse(ctx));
      }
    } else if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      // 存在一段时间未进行数据的发送和读取，关闭通道
      if (IdleState.ALL_IDLE.equals(event.state())) {
        ctx.channel().close();
      }
      // 有效时间内未发生数据的读取
      if (IdleState.READER_IDLE.equals(event.state())) {
        ctx.channel().close();
      }
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

  /**
   * 构建ChannelRequest对象
   *
   * @param action
   * @param channelHandlerContext 上下文
   * @return
   */
  private ChannelRequest buildChannelRequest(ChannelHandlerContext channelHandlerContext,
      String action) {
    ChannelRequest channelRequest = new ChannelRequest();
    channelRequest.setAction(action);
    channelRequest.setChannelHandlerContext(channelHandlerContext);
    return channelRequest;
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    super.handlerRemoved(ctx);
//    NettyContext.getInstance().removeUserChannel(ctx.channel());
  }
}
