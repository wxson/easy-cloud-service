package com.easy.cloud.web.module.netty.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author GR
 */
@Getter
@Setter
@Component
@RefreshScope
@ConfigurationProperties(prefix = "easy.cloud.netty")
public class NettyServerConfigProperties {

  /**
   * 端口
   */
  private int port = 8088;
  /**
   * 链接线程的数量，默认1
   **/
  private int bossThreads = 1;
  /**
   * 工作线程的数量，默认4
   **/
  private int workThreads = 4;
  /**
   * 发送大小
   */
  private int sendBuffSize = 65535;
  /**
   * 接收大小
   */
  private int receiveBuffSize = 65535;
  /**
   * 读取事件空闲时间（未发生任务读事件）,单位秒
   */
  private int readerIdleTime = 60;
  /**
   * 连接最大空闲时间（未发生数据的读写）,单位秒
   */
  private int maxIdleTime = 120;
  /**
   * 全局客户端请求QPS限制
   */
  private int requestQps = 5000;
}
