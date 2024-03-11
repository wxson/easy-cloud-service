package com.easy.cloud.web.module.netty.domain;

import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.module.netty.annotation.ActionMessage;
import com.easy.cloud.web.module.netty.utils.ActionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.Objects;
import lombok.experimental.UtilityClass;

/**
 * 渠道广播
 *
 * @author GR
 * @date 2023/5/31 18:17
 */
@UtilityClass
public class ChannelBroadcast {

  /**
   * 广播消息
   *
   * @param channel 通道
   * @param message 信息
   */
  public void sendBroadcast(Channel channel, Object message) {
    String content;
    if (message instanceof String) {
      content = message.toString();
    } else {
      content = JSONUtil.toJsonStr(message);
    }
    // 获取消息注解
    ActionMessage actionMessage = message.getClass().getAnnotation(ActionMessage.class);
    if (Objects.nonNull(actionMessage)) {
      String actionMessagePath = actionMessage.value();
      StringBuffer buffer = new StringBuffer();
      buffer.append("{\"action\":\"")
          .append(actionMessagePath)
          .append("\",\"body\":")
          .append(content).append("}");
      content = ActionUtil.formatPath(buffer.toString());
    }
    // textWebSocketFrame 数据格式
    TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(content);
    channel.writeAndFlush(textWebSocketFrame);
  }
}
