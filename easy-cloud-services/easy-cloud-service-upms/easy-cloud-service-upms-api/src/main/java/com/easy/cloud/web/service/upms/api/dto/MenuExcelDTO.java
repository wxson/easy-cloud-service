package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import java.util.List;
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
public class MenuExcelDTO implements IConverter {

  /**
   * 文档ID
   */
  private String id;
  /**
   * 类型（M目录 C菜单 F按钮）
   */
  private MenuTypeEnum type;

  /**
   * 路由父级ID
   */
  private String parentId;

  /**
   * 路由图标
   */
  private String icon;

  /**
   * 路由路径
   */
  private String path;

  /**
   * 菜单名称
   */
  private String name;

  /**
   * 组件名称，用于加载layout
   */
  private String component;

  /**
   * 菜单搜索名称
   */
  private String title;

  /**
   * 权限标识
   */
  private String perms;

  /**
   * 是否超链接菜单
   */
  private Boolean isLink;

  /**
   * 链接地址
   */
  private String linkUrl;

  /**
   * 是否隐藏
   */
  private Boolean isHidden;

  /**
   * 是否缓存组件状态
   */
  private Boolean isKeepAlive;

  /**
   * 是否固定在 tagsView 栏上
   */
  private Boolean isAffix;

  /**
   * 是否内嵌窗口，开启条件，`1、isIframe:true 2、isLink：链接地址不为空`
   */
  private Boolean isIframe;

  /**
   * 重定向路由
   */
  private String redirect;
  /**
   * 排序字段，数值越小越排靠前
   */
  private Integer sort;

  /**
   * 子菜单
   */
  private List<MenuExcelDTO> children;
}