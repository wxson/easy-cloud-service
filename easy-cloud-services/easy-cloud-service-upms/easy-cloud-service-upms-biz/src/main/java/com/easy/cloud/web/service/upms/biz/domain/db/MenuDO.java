package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.component.core.annotation.DefaultField;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.biz.domain.vo.MenuVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 菜单表
 * 新增菜单比新增一条菜单权限数据
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_menu")
public class MenuDO implements IConverter<MenuVO> {
    /**
     * 文档ID
     */
    private String id;
    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身
     * 一个企业、一个单位或一所学校只能有一个租户
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
     * 标题
     */
    private String title;
    /**
     * 是否隐藏 0 是 1 否
     */
    private Integer hidden;
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
    @DefaultField
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
    private String createAt;
    /**
     * 更新人员
     */
    private String updaterAt;
    /**
     * 更新时间
     */
    private String updateAt;
}
