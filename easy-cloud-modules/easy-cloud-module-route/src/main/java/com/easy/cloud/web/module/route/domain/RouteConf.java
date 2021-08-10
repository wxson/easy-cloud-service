package com.easy.cloud.web.module.route.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.service.IConvertProxy;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 路由信息表
 *
 * @author GR
 * @TableName route_conf
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@TableName("route_conf")
public class RouteConf implements IConvertProxy {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 断言，匹配，格式为JSON 字符串数组
     * 如：Path=/goods/**；网关自动匹配当前请求path，若包含/goods，则会自动匹配到mall-goods服务，并uri为lb://mall-goods的服务
     */
    private String predicates;
    /**
     * 路由ID，可自定义，不写系统会自动按一定算法进行赋值
     */
    private String routeId;

    /**
     * 过滤规则
     */
    private String filters;

    /**
     * 断言，匹配，格式为JSON 字符串数组
     */
    private String uri;

    /**
     * 断言，匹配，格式为JSON 字符串数组
     */
    private String routeName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;

    /**
     * 是否删除 0 未删除 1 已删除
     */
    private Integer deleted;

    /**
     * 创建用户
     */
    private String creatorAt;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    private static final long serialVersionUID = 1L;

}