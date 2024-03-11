package com.easy.cloud.web.module.netty.annotation;

import com.easy.cloud.web.module.netty.NettyBootstrap;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 是否允许Netty
 *
 * @author GR
 * @date 2023/5/17 11:30
 */
@Import(NettyBootstrap.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnableNettyServer {
//    /**
//     * websocket
//     */
//    NetType type() default NetType.HTTP;
//
//    /**
//     * 网络类型
//     */
//    enum NetType {
//        HTTP,
//        WEB_SOCKET,
//    }
}
