package com.easy.cloud.web.module.netty;

import com.easy.cloud.web.module.netty.configuration.ActionEndpointManager;
import com.easy.cloud.web.module.netty.configuration.NettyServerConfigProperties;
import com.easy.cloud.web.module.netty.handler.RequestConfirmHandler;
import com.easy.cloud.web.module.netty.handler.RequestDispatchHandler;
import com.easy.cloud.web.module.netty.handler.RequestHandshakeFilterHandler;
import com.easy.cloud.web.module.netty.handler.RequestRateLimiterFilterHandler;
import com.easy.cloud.web.module.netty.handler.WebSocketDecoderHandler;
import com.easy.cloud.web.module.netty.handler.WebSocketEncoderHandler;
import com.easy.cloud.web.module.netty.utils.NetUtil;
import com.google.common.util.concurrent.RateLimiter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.InetSocketAddress;
import java.util.Objects;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Netty 启动器
 *
 * @author GR
 */
@Slf4j
@Component
public class NettyBootstrap {

  @Autowired
  private NettyServerConfigProperties nettyConfigProperties;

  @Autowired
  private ActionEndpointManager actionEndpointManager;

  /**
   * 连接组
   */
  private NioEventLoopGroup bossGroup = null;
  /**
   * 工作组
   */
  private NioEventLoopGroup workerGroup = null;

  //  @PostConstruct
  public void init() {
    this.start();
  }

  /**
   * 初始化后启动服务
   */
  @Async
  public void start() {
    bossGroup = new NioEventLoopGroup(nettyConfigProperties.getBossThreads());
    workerGroup = new NioEventLoopGroup(nettyConfigProperties.getWorkThreads());
    int port = this.nettyConfigProperties.getPort();
    ServerBootstrap bootstrap = new ServerBootstrap();
    try {
      bootstrap.group(bossGroup, workerGroup)
          .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .option(ChannelOption.SO_REUSEADDR, true)
          .option(ChannelOption.SO_KEEPALIVE, false)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_SNDBUF, nettyConfigProperties.getSendBuffSize())
          .childOption(ChannelOption.SO_RCVBUF, nettyConfigProperties.getReceiveBuffSize())
          .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
          .localAddress(new InetSocketAddress(port)).childHandler(createChannelInitializer());
      log.info("开始启动服务，端口:{}", port);
      ChannelFuture channelFuture = bootstrap.bind(port);
      channelFuture.addListener(future -> log.info("---- Easy Cloud Netty 启动成功 ----"));
      channelFuture.channel().closeFuture().sync();
    } catch (Exception e) {
      log.error("Easy Cloud Netty 启动失败：{}", e);
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  /**
   * 使用多路复用机制
   *
   * @return
   */
  private boolean useEpoll() {
    return NetUtil.isLinuxPlatform() && Epoll.isAvailable();
  }

  /**
   * 连接channel初始化的时候调用
   *
   * @return
   */
  private ChannelInitializer<Channel> createChannelInitializer() {
    return new ChannelInitializer<Channel>() {
      @Override
      protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        try {
          // 获取空闲时间
          int allIdleTimeSeconds = nettyConfigProperties.getMaxIdleTime();
          int readerIdleTime = nettyConfigProperties.getReaderIdleTime();
          pipeline.addLast(new IdleStateHandler(readerIdleTime, 0, allIdleTimeSeconds));
          //因为基于http协议，使用http的编码和解码器
          pipeline.addLast(new HttpServerCodec());
          //是以块方式写，添加ChunkedWriteHandler处理器
          pipeline.addLast(new ChunkedWriteHandler());
          pipeline.addLast(new HttpObjectAggregator(8192));
          pipeline.addLast(new WebSocketServerProtocolHandler("/ws", true));
          // 创建QPS限制器
          RateLimiter rateLimiter = RateLimiter.create(nettyConfigProperties.getRequestQps());
          pipeline.addLast(new RequestRateLimiterFilterHandler(rateLimiter));
          pipeline.addLast(new RequestHandshakeFilterHandler(actionEndpointManager));
          pipeline.addLast(new WebSocketDecoderHandler());
          pipeline.addLast(new WebSocketEncoderHandler());
          pipeline.addLast(new RequestConfirmHandler());
          pipeline.addLast(new RequestDispatchHandler(actionEndpointManager));
        } catch (Exception e) {
          log.error("createChannelInitializer：{}", e.getMessage());
          channel.close();
        }
      }
    };
  }

  /**
   * 销毁释放
   */
  @PreDestroy
  public void destroy() {
    if (Objects.nonNull(bossGroup)) {
      bossGroup.shutdownGracefully();
    }
    if (Objects.nonNull(workerGroup)) {
      workerGroup.shutdownGracefully();
    }
  }
}
