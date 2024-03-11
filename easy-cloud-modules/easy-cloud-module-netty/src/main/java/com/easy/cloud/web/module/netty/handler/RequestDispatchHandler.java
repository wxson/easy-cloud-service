package com.easy.cloud.web.module.netty.handler;

import com.easy.cloud.web.module.netty.configuration.ActionEndpointManager;
import com.easy.cloud.web.module.netty.domain.ChannelRequest;
import com.easy.cloud.web.module.netty.domain.ChannelResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求分发
 *
 * @author GR
 * @date 2023/5/17 16:00
 */
@Slf4j
public class RequestDispatchHandler extends ChannelInboundHandlerAdapter {

    private final ActionEndpointManager actionEndpointManager;

    public RequestDispatchHandler(ActionEndpointManager actionEndpointManager) {
        this.actionEndpointManager = actionEndpointManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ChannelRequest) {
            ChannelRequest channelRequest = (ChannelRequest) msg;
            Object invoke = actionEndpointManager.invoke(channelRequest, new ChannelResponse(ctx));
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //接收channel内部的一些事件
        if (evt instanceof IdleStateEvent) {
            log.debug("channel idle and to be close,{}", ctx.channel().id().asShortText());
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 输出异常信息
        cause.printStackTrace();
        ctx.close();
    }

}
