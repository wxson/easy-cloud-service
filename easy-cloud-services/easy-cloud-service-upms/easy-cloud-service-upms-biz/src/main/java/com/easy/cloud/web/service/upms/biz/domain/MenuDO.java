package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.domain.BaseEntity;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Menu 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@Entity
@Data
@SuperBuilder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EnableLogic
@Table(name = "db_menu")
public class MenuDO extends BaseEntity {

  /**
   * 类型（M目录 C菜单 F按钮）
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '类型（M目录 C菜单 F按钮）'")
  private MenuTypeEnum type;

  /**
   * 路由父级ID
   */
  @Column(columnDefinition = "VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '父级ID'")
  private String parentId;

  /**
   * 路由图标
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '路由图标'")
  private String icon;

  /**
   * 路由路径
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '路由路径'")
  private String path;

  /**
   * 路由名称
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '路由名称'")
  private String name;

  /**
   * 组件名称，用于加载layout
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '组件名称，用于加载layout'")
  private String component;

  /**
   * 菜单搜索名称
   */
  @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '菜单搜索名称'")
  private String title;

  /**
   * 权限标识
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '权限标识'")
  private String perms;

  /**
   * 是否超链接菜单
   */
  @Column(name = "link", columnDefinition = "TINYINT DEFAULT '0' COMMENT '是否超链接菜单'")
  private Boolean isLink;

  /**
   * 链接地址
   */
  @Column(columnDefinition = "VARCHAR(225) COMMENT '链接地址'")
  private String linkUrl;

  /**
   * 是否隐藏
   */
  @Column(name = "hidden", columnDefinition = "TINYINT DEFAULT '0' COMMENT '是否隐藏头'")
  private Boolean isHidden;

  /**
   * 是否缓存组件状态
   */
  @Column(name = "keep_alive", columnDefinition = "TINYINT DEFAULT '0' COMMENT '是否缓存组件状态'")
  private Boolean isKeepAlive;

  /**
   * 是否固定在 tagsView 栏上
   */
  @Column(name = "affix", columnDefinition = "TINYINT DEFAULT '0' COMMENT '是否固定在 tagsView 栏上'")
  private Boolean isAffix;

  /**
   * 是否内嵌窗口，开启条件，`1、isIframe:true 2、isLink：链接地址不为空`
   */
  @Column(name = "iframe", columnDefinition = "TINYINT DEFAULT '0' COMMENT '是否内嵌窗口，开启条件，`1、isIframe:true 2、isLink：链接地址不为空`'")
  private Boolean isIframe;

  /**
   * 重定向路由
   */
  @Column(columnDefinition = "VARCHAR(125) COMMENT '重定向路由'")
  private String redirect;
  /**
   * 排序字段，数值越小越排靠前
   */
  @Column(columnDefinition = "INT NOT NULL DEFAULT '0' COMMENT '排序'")
  private Integer sort;
}