package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "MenuDTO", description = "菜单入参")
public class MenuDTO implements IConverter {

    /**
     * 文档ID
     */
    @ApiModelProperty(value = "文档ID", required = false)
    private String id;
    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "类型（0目录 1菜单 2按钮）", required = false)
    private MenuTypeEnum type;

    /**
     * 路由父级ID
     */
    @ApiModelProperty(value = "路由父级ID", required = false)
    private String parentId;

    /**
     * 路由图标
     */
    @ApiModelProperty(value = "路由图标", required = false)
    private String icon;

    /**
     * 路由路径
     */
    @ApiModelProperty(value = "路由路径", required = true)
    private String path;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    /**
     * 组件名称，用于加载layout
     */
    @ApiModelProperty(value = "组件名称", required = true)
    private String component;

    /**
     * 菜单搜索名称
     */
    @ApiModelProperty(value = "菜单搜索名称", required = true)
    private String title;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", required = false)
    private String perms;

    /**
     * 是否超链接菜单
     */
    @ApiModelProperty(value = "是否超链接菜单", required = false)
    private Boolean isLink;

    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址", required = false)
    private String linkUrl;

    /**
     * 是否隐藏
     */
    @ApiModelProperty(value = "是否隐藏", required = false)
    private Boolean isHidden;

    /**
     * 是否缓存组件状态
     */
    @ApiModelProperty(value = "是否缓存组件状态", required = false)
    private Boolean isKeepAlive;

    /**
     * 是否固定在 tagsView 栏上
     */
    @ApiModelProperty(value = "是否固定在 tagsView 栏上", required = false)
    private Boolean isAffix;

    /**
     * 是否内嵌窗口，开启条件，`1、isIframe:true 2、isLink：链接地址不为空`
     */
    @ApiModelProperty(value = "是否内嵌窗口", required = false)
    private Boolean isIframe;

    /**
     * 重定向路由
     */
    @ApiModelProperty(value = "重定向路由", required = false)
    private String redirect;
    /**
     * 排序字段，数值越小越排靠前
     */
    @ApiModelProperty(value = "排序字段，数值越小越排靠前", required = false)
    private Integer sort;
}