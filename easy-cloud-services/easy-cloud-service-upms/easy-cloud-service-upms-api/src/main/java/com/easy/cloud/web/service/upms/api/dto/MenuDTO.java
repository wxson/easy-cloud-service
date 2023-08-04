package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Menu请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO implements IConverter {

  /**
   * 文档ID
   */
  private Long id;
  /**
   * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身 一个企业、一个单位或一所学校只能有一个租户
   */
  private String tenantId;
  /**
   * 路由名称
   */
  private String name;
  /**
   * 路由路径
   */
  private String path;
  /**
   * 路由父级ID
   */
  private String parentId;
  /**
   * 路由图标
   */
  private String icon;
  /**
   * 路由
   */
  private String route;
  /**
   * 权限字符串
   */
  private String permissionTag;

  /**
   * 类型（M目录 C菜单 F按钮）
   */
  private MenuTypeEnum type;
  /**
   * 是否隐藏 0 是 1 否
   */
  private Integer visible;
  /**
   * _blank|_self|_top|_parent
   */
  private String target;
  /**
   * 保持连接 0 是 1 否
   */
  private Integer keepAlive;
  /**
   * 是否隐藏头信息 0 是 1 否
   */
  private Integer hiddenHeader;
  /**
   * 组件名称，用于加载view
   */
  private String tag;
  /**
   * 组件名称，用于加载layout
   */
  private String component;
  /**
   * 重定向路由
   */
  private String redirect;
  /**
   * 排序字段，数值越小越排靠前
   */
  private Integer sort;
  /**
   * 状态 0 启用 1 禁用
   */
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  private DeletedEnum deleted;
  /**
   * 创建用户
   */
  private String createBy;
  /**
   * 创建时间
   */
  private String createAt;
  /**
   * 更新人员
   */
  private String updateBy;
  /**
   * 更新时间
   */
  private String updateAt;
}