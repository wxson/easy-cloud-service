package com.easy.cloud.web.module.route.domain;

import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 路由信息表
 *
 * @author GR
 * @TableName route_conf
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "db_route_conf")
public class RouteConf extends BaseEntity {

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

}