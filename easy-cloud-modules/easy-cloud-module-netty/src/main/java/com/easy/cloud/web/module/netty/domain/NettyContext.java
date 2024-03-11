package com.easy.cloud.web.module.netty.domain;

import io.netty.channel.Channel;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * Netty 上下文
 *
 * @author GR
 * @date 2023/5/18 18:27
 */
public class NettyContext {

  private static NettyContext nettyContext;

  private static ConcurrentMap<String, Channel> channels = new ConcurrentHashMap<>();

  /**
   * user custom key-value：key is user unique id，value is channelId
   */
  private static ConcurrentMap<String, String> userChannels = new ConcurrentHashMap<>();

  /**
   * 获取单例对象
   *
   * @return
   */
  public static NettyContext getInstance() {
    if (Objects.isNull(nettyContext)) {
      synchronized (NettyContext.class) {
        if (Objects.isNull(nettyContext)) {
          nettyContext = new NettyContext();
        }
      }
    }
    return nettyContext;
  }

  public Map<String, Channel> getAllChannelMap() {
    return channels;
  }

  public Collection<Channel> getAllChannels() {
    return channels.values();
  }

//  public Optional<Channel> getChannel(String channelId) {
//    return Optional.ofNullable(channels.get(channelId));
//  }

//  public void addChannel(String channelId, Channel channel) {
//    channels.put(channelId, channel);
//  }

//  public void removeChannel(String channelId) {
//    channels.remove(channelId);
//  }

  /**
   * 获取通道信息
   *
   * @param uniqueKey 唯一标识
   * @return
   */
  public Optional<Channel> getUserChannel(String uniqueKey) {
    return Optional.ofNullable(channels.get(uniqueKey));
  }

  /**
   * 手动添加channel
   *
   * @param uniqueKey 唯一标识
   * @param channel   通道
   */
  public void addUserChannel(String uniqueKey, Channel channel) {
    if (StringUtils.isNotBlank(uniqueKey)){
      channels.put(uniqueKey, channel);
    }
  }

  /**
   * 手动移除channel
   *
   * @param uniqueKey 唯一标识
   */
  public void removeUserChannel(String uniqueKey) {
    if (StringUtils.isNotBlank(uniqueKey) && userChannels.containsKey(uniqueKey)) {
      String channelId = userChannels.get(uniqueKey);
      channels.remove(channelId);
    }
  }

  /**
   * 断开连接，自动移除channel
   *
   * @param channel 通道信息
   */
//  public void removeUserChannel(Channel channel) {
//    String channelId = channel.id().asLongText();
//    channels.remove(channelId);
//    // 便利移除
//    Set<String> uniqueIds = userChannels.entrySet()
//        .stream().filter(entry -> entry.getValue().equals(channelId))
//        .map(entry -> entry.getKey())
//        .collect(Collectors.toSet());
//    uniqueIds.forEach(uniqueId -> channels.remove(uniqueId));
//  }
}
