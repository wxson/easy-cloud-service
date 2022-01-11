package com.easy.cloud.web.service.upms.biz.domain.vo;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
@ApiModel(value = "菜单数据", description = "菜单返回数据")
public class MenuVO implements Serializable {
    /**
     * 文档ID
     */
    @ApiModelProperty(name = "id", value = "文档ID")
    private String id;
    /**
     * 路由名称
     */
    @ApiModelProperty(name = "name", value = "路由名称")
    private String name;
    /**
     * 路由路径
     */
    @ApiModelProperty(name = "path", value = "路由路径")
    private String path;
    /**
     * 路有父级ID
     */
    @ApiModelProperty(name = "parentId", value = "路有父级ID")
    private String parentId;
    /**
     * 路由图标
     */
    @ApiModelProperty(name = "icon", value = "路由图标")
    private String icon;
    /**
     * 标题
     */
    @ApiModelProperty(name = "title", value = "标题")
    private String title;
    /**
     * 是否显示
     */
    @ApiModelProperty(name = "show", value = "是否显示")
    private Boolean show;
    /**
     * _blank|_self|_top|_parent
     */
    @ApiModelProperty(name = "target", value = "_blank|_self|_top|_parent")
    private String target;
    /**
     * 保持活跃
     */
    @ApiModelProperty(name = "keepAlive", value = "保持活跃")
    private Boolean keepAlive;
    /**
     * 隐藏头部内容
     */
    @ApiModelProperty(name = "hiddenHeaderContent", value = "隐藏头部内容")
    private Boolean hiddenHeaderContent;
    /**
     * 组件名称，用于加载view
     */
    @ApiModelProperty(name = "key", value = "组件名称，用于加载view")
    private String key;
    /**
     * 组件名称，用于加载layout
     */
    @ApiModelProperty(name = "component", value = "组件名称，用于加载layout")
    private String component;
    /**
     * 重定向路由
     */
    @ApiModelProperty(name = "redirect", value = "重定向路由")
    private String redirect;
    /**
     * 排序字段，数值越小越排靠前
     */
    @ApiModelProperty(name = "sort", value = "排序字段，数值越小越排靠前")
    private Integer sort;
    /**
     * 逻辑删除标记 0：未删除     1：已删除
     */
    @ApiModelProperty(name = "deleted", value = "逻辑删除标记 0：未删除     1：已删除")
    private DeletedEnum deleted;
    /**
     * 启用状态
     */
    @ApiModelProperty(name = "status", value = "启用状态")
    private StatusEnum status;
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
    /**
     * 权限Action集合
     */
    private List<PermissionActionBO> actions;
    /**
     * 子集和
     */
    private List<MenuVO> children;
}
