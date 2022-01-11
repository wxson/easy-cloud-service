package com.easy.cloud.web.service.upms.biz.domain.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 部门权限关系表
 *
 * @author GR
 * @date 2020-11-4 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("db_relation_department_permission")
public class RelationDepartmentPermissionDO implements Serializable {
    /**
     * ID
     */
    private String id;
    /**
     * 部门ID
     */
    private String departmentId;
    /**
     * 权限ID
     */
    private String permissionId;
    /**
     * 角色权限操作：新增、查询、删除等
     */
    @TableField(exist = false)
    private List<PermissionActionBO> actions;
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
}
