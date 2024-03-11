package com.easy.cloud.web.module.netty.handler;

import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.module.netty.domain.ChannelRequest;
import com.easy.cloud.web.module.netty.utils.ActionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * WebSocket 解码器
 *
 * @author GR
 * @date 2023/5/18 15:40
 */
@Slf4j
public class WebSocketDecoderHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof TextWebSocketFrame) {
      try {
        ByteBuf byteBuf = ((TextWebSocketFrame) msg).content();
        final String content = byteBuf.toString(CharsetUtil.UTF_8);
        ChannelRequest channelRequest = this.buildChannelRequest(ctx, content);
        ctx.fireChannelRead(channelRequest);
        byteBuf.release();
      } catch (Throwable e) {
        String message = String.format("read message error：%s", e.getMessage());
        log.error(message);
        ctx.fireExceptionCaught(new BusinessException(message));
      }
    } else {
      ctx.fireChannelRead(msg);
    }
  }

  private ChannelRequest buildChannelRequest(ChannelHandlerContext ctx, String content) {
    if (StringUtils.isEmpty(content)) {
      content = "{}";
    }
    // 创建对象载体
    ChannelRequest channelRequest;
    try {
      channelRequest = JSONUtil.toBean(content, ChannelRequest.class);
    } catch (Exception exception) {
      log.error("数据格式转换错误：{}", exception.getMessage());
      // 构建默认数据
      channelRequest = new ChannelRequest();
    }

    // 设置上下文
    channelRequest.setChannelHandlerContext(ctx);
    // 获取路由数据
    String action = channelRequest.getAction();
    // 构建路由参数
    QueryStringDecoder queryDecoder = new QueryStringDecoder(action);
    channelRequest.setUrlParams(queryDecoder.parameters());
    // 事件
    channelRequest.setAction(ActionUtil.getUniqueActionPath(action));
    return channelRequest;
  }
}
