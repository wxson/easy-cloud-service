package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import com.easy.cloud.web.service.upms.biz.enums.PermissionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.util.List;

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
@TableName("db_permission")
public class PermissionDO {
    /**
     * ID
     * 注意：该ID必须全局唯一
     */
    private String id;
    /**
     * 目标ID，若当前为menu权限，则targetId指向当前的menuId
     */
    private String targetId;    /**
     * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身
     * 一个企业、一个单位或一所学校只能有一个租户
     */
    private String tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 权限类型：menu、department、application、api。。。
     */
    private Integer type;
    /**
     * 角色权限操作：新增、查询、删除等，存储时为空，因为该字段为一个变量，每隔角色的actions都有可能不一样
     */
    @TableField(exist = false)
    private List<PermissionActionBO> actions;
    /**
     * 排序字段，数值越小越排靠前
     */
    private Integer sort;
    /**
     * 状态 0 启用 1 禁用
     */
    private Integer status;
    /**
     * 逻辑删除标记 0：未删除     1：已删除
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
