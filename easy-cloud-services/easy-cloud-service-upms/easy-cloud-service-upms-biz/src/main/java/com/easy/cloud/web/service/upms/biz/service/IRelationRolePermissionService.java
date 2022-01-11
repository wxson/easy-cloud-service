package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import com.easy.cloud.web.service.upms.biz.domain.db.RelationRolePermissionDO;
import com.easy.cloud.web.service.upms.biz.mapper.RelationRolePermissionMapper;

import java.io.Serializable;
import java.util.List;

/**
 * 角色权限业务逻辑关系
 *
 * @author GR
 * @date 2020-11-4 15:17
 */
public interface IRelationRolePermissionService {

    /**
     * 保存角色权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    default void saveRelationRolePermission(String roleId, String permissionId) {
        Assert.notBlank(roleId, "保存角色权限时，角色ID为空");
        Assert.notBlank(permissionId, "保存角色权限时，权限ID为空");
        RelationRolePermissionDO relationRolePermissionDO = RelationRolePermissionDO.builder().roleId(roleId).permissionId(permissionId)
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build();
        SpringContextHolder.getBean(RelationRolePermissionMapper.class).insert(relationRolePermissionDO);
    }

    /**
     * 保存角色权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @param actions      操作ID
     */
    default void saveRelationRolePermission(String roleId, String permissionId, List<PermissionActionBO> actions) {
        Assert.notBlank(roleId, "保存角色权限时，角色ID为空");
        Assert.notBlank(permissionId, "保存角色权限时，权限ID为空");
        RelationRolePermissionDO relationRolePermissionDO = RelationRolePermissionDO.builder().roleId(roleId).permissionId(permissionId)
                // 默认的权限操作
                .actions(actions)
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build();
        SpringContextHolder.getBean(RelationRolePermissionMapper.class).insert(relationRolePermissionDO);
    }

    /**
     * 移除所有持有当前权限的信息，不指定角色
     * 如：删除一个菜单之后，当前所有持有该菜单权限的用户都不在拥有改菜单的权限
     *
     * @param permissionId 权限ID
     */
    default void removeRelationRolePermissionByPermissionId(String permissionId) {
        SpringContextHolder.getBean(RelationRolePermissionMapper.class).delete(Wrappers.<RelationRolePermissionDO>lambdaQuery().eq(RelationRolePermissionDO::getPermissionId, permissionId));
    }

    /**
     * 移除角色的某一个权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    default void removeRelationRolePermissionByRoleIdAndPermissionId(String roleId, String permissionId) {
        SpringContextHolder.getBean(RelationRolePermissionMapper.class).delete(Wrappers.<RelationRolePermissionDO>lambdaQuery().eq(RelationRolePermissionDO::getRoleId, roleId).eq(RelationRolePermissionDO::getPermissionId, permissionId));
    }

    /**
     * 移除角色的相关权限
     *
     * @param roleId           角色ID
     * @param permissionIdList 权限ID集合
     */
    default void removeRelationRolePermissionByRoleIdAndPermissionList(String roleId, List<String> permissionIdList) {
        SpringContextHolder.getBean(RelationRolePermissionMapper.class).delete(Wrappers.<RelationRolePermissionDO>lambdaQuery().eq(RelationRolePermissionDO::getRoleId, roleId).in(RelationRolePermissionDO::getPermissionId, permissionIdList));
    }

    /**
     * 根据角色ID移除角色相关权限
     *
     * @param roleId 角色ID
     */
    default void removeRelationRolePermissionByRoleId(Serializable roleId) {
        SpringContextHolder.getBean(RelationRolePermissionMapper.class).delete(Wrappers.<RelationRolePermissionDO>lambdaQuery().eq(RelationRolePermissionDO::getRoleId, roleId));
    }

    /**
     * 根据角色查询权限
     *
     * @param roleId           角色ID
     * @param permissionIdList 权限ID集合
     * @return java.util<com.easy.cloud.web.upms.biz.domain.db.RelationRolePermissionDO>
     */
    default List<RelationRolePermissionDO> findRelationRolePermissionByRoleIdAndInPermissionId(String roleId, List<String> permissionIdList) {
        if (CollUtil.isEmpty(permissionIdList)) {
            return CollUtil.newArrayList();
        }
        return SpringContextHolder.getBean(RelationRolePermissionMapper.class).selectList(Wrappers.<RelationRolePermissionDO>lambdaQuery()
                .eq(RelationRolePermissionDO::getRoleId, roleId)
                .in(RelationRolePermissionDO::getPermissionId, permissionIdList));
    }

}
