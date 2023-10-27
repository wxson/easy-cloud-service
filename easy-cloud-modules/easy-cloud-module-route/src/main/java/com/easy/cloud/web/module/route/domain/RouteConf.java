package com.easy.cloud.web.module.route.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 路由信息表
 *
 * @author GR
 * @TableName route_conf
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "route_conf")
public class RouteConf {

  /**
   * 文档ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  /**
   * 断言，匹配，格式为JSON 字符串数组 如：Path=/goods/**；网关自动匹配当前请求path，若包含/goods，则会自动匹配到mall-goods服务，并uri为lb://mall-goods的服务
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
  private Integer sort = 0;

  /**
   * 状态 0 启用 1 禁用
   */
  private Integer status = 0;

  /**
   * 是否删除 0 未删除 1 已删除
   */
  private Integer deleted = 0;

  /**
   * 创建用户
   */
  private String creatorAt;

  /**
   * 创建时间
   */
  private LocalDateTime createAt;

  /**
   * 更新时间
   */
  private LocalDateTime updateAt;

  private static final long serialVersionUID = 1L;

}