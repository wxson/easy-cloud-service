package com.easy.cloud.web.module.netty.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.module.netty.domain.ChannelRequest;
import com.easy.cloud.web.module.netty.domain.ChannelResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * ActionEndpoint对象
 *
 * @author GR
 * @date 2023/5/18 17:51
 */
@Data
@Slf4j
@AllArgsConstructor
public class ActionEndpointProxy {

    /**
     * 目标实例
     */
    private Object bean;
    /**
     * 方法
     */
    private Method method;

    /**
     * 方法参数类别
     */
    private Class<?>[] args;

    /**
     * 方法调用
     *
     * @param channelRequest
     * @param channelResponse
     * @return
     */
    public Object invoke(ChannelRequest channelRequest, ChannelResponse channelResponse)
            throws InvocationTargetException, IllegalAccessException {
        if (Objects.nonNull(bean) && Objects.nonNull(method)) {
            List<Object> objs = CollUtil.newArrayList();
            for (Class<?> arg : args) {
                // 是否存在ChannelHandlerContext的子类
                if (ChannelHandlerContext.class.isAssignableFrom(arg)) {
                    objs.add(channelResponse.getChannelHandlerContext());
                    continue;
                }
                // ChannelRequest
                if (ChannelRequest.class.isAssignableFrom(arg)) {
                    objs.add(channelRequest);
                    continue;
                }
                // ChannelResponse
                if (ChannelResponse.class.isAssignableFrom(arg)) {
                    objs.add(channelResponse);
                    continue;
                }
                // 其他
                if (Objects.nonNull(channelRequest.getBody())) {
                    objs.add(JSONUtil.toBean(channelRequest.getBody().toString(), arg));
                } else {
                    // 构建一个空对象
                    objs.add(JSONUtil.toBean(JSONUtil.createObj(), arg));
                }
            }
            // 调用方法
            return this.method.invoke(this.bean, objs.toArray());
        }
        return null;
    }
}
