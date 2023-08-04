package com.easy.cloud.web.service.upms.biz.domain;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Menu 持久类
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@Entity
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_menu")
public class MenuDO implements IConverter {

  /**
   * 文档ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * 菜单名称
   */
  @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '菜单名称'")
  private String name;
  /**
   * 路由
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '路由'")
  private String route;
  /**
   * 权限字符串
   */
  @Column(columnDefinition = "VARCHAR(255) COMMENT '权限字符串'")
  private String permissionTag;
  /**
   * 路由路径
   */
  @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '路由路径'")
  private String path;
  /**
   * 路由父级ID
   */
  @Column(columnDefinition = "LONG NOT NULL DEFAULT '0' COMMENT '路由路径'")
  private Long parentId;
  /**
   * 路由图标
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '路由图标'")
  private String icon;
  /**
   * 类型（M目录 C菜单 F按钮）
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL COMMENT '权限字符串'")
  private MenuTypeEnum type;
  /**
   * 是否隐藏 0 是 1 否
   */
  @Column(columnDefinition = "TINYINT(0) DEFAULT '0' COMMENT '是否隐藏 0 是 1 否'")
  private Integer visible;
  /**
   * _blank|_self|_top|_parent
   */
  @Column(columnDefinition = "VARCHAR(64) COMMENT '_blank|_self|_top|_parent'")
  private String target;
  /**
   * 保持连接 0 是 1 否
   */
  @Column(columnDefinition = "TINYINT(0) DEFAULT '0' COMMENT '保持连接 0 是 1 否'")
  private Integer keepAlive;
  /**
   * 是否隐藏头信息 0 是 1 否
   */
  @Column(columnDefinition = "TINYINT(0) DEFAULT '0' COMMENT '是否隐藏头信息 0 是 1 否'")
  private Integer hiddenHeader;
  /**
   * 组件名称，用于加载view
   */
  @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '组件名称，用于加载view'")
  private String tag;
  /**
   * 组件名称，用于加载layout
   */
  @Column(columnDefinition = "VARCHAR(125) NOT NULL COMMENT '组件名称，用于加载layout'")
  private String component;
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
  /**
   * 状态 0 启用 1 禁用
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'START_STATUS' COMMENT '状态'")
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(64) NOT NULL DEFAULT 'UN_DELETED' COMMENT '是否删除'")
  private DeletedEnum deleted;
  /**
   * 创建用户
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建用户'")
  private String createBy;
  /**
   * 创建时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '创建时间'")
  private String createAt;
  /**
   * 更新人员
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新人员'")
  private String updateBy;
  /**
   * 更新时间
   */
  @Column(columnDefinition = "VARCHAR(32) COMMENT '更新时间'")
  private String updateAt;
}