package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.db.RelationUserRoleDO;
import com.easy.cloud.web.service.upms.biz.mapper.RelationUserRoleMapper;

import java.io.Serializable;
import java.util.List;

/**
 * 用户权限业务逻辑关系
 *
 * @author GR
 * @date 2020-11-4 15:17
 */
public interface IRelationUserRoleService {

    /**
     * 保存用户角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    default void saveRelationUserRole(String userId, String roleId) {
        Assert.notBlank(userId, "保存用户角色时，用户ID为空");
        Assert.notBlank(roleId, "保存用户角色时，角色ID为空");
        RelationUserRoleDO relationUserRoleDO = RelationUserRoleDO.builder().userId(userId).roleId(roleId)
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build();
        SpringContextHolder.getBean(RelationUserRoleMapper.class).insert(relationUserRoleDO);
    }

    /**
     * 保存用户角色
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID集合
     */
    default void saveRelationUserRoleList(String userId, List<String> roleIdList) {
        Assert.notBlank(userId, "保存用户角色时，用户ID为空");
        roleIdList.forEach(roleId -> SpringContextHolder.getBean(RelationUserRoleMapper.class).insert(RelationUserRoleDO.builder().userId(userId).roleId(roleId)
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build()));
    }

    /**
     * 根据角色Id移除用户角色
     *
     * @param roleId 角色ID
     */
    default void removeRelationUserRoleByRoleId(Serializable roleId) {
        SpringContextHolder.getBean(RelationUserRoleMapper.class).delete(Wrappers.<RelationUserRoleDO>lambdaQuery().eq(RelationUserRoleDO::getRoleId, roleId));
    }

    /**
     * 根据用户Id移除用户角色
     *
     * @param userId 用户ID
     */
    default void removeRelationUserRoleByUserId(Serializable userId) {
        SpringContextHolder.getBean(RelationUserRoleMapper.class).delete(Wrappers.<RelationUserRoleDO>lambdaQuery().eq(RelationUserRoleDO::getUserId, userId));
    }

    /**
     * 根据角色查询权限
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID集合
     * @return java.util<com.easy.cloud.web.upms.biz.domain.db.RelationUserPermissionDO>
     */
    default List<RelationUserRoleDO> findRelationUserRoleByUserIdAndInRoleId(String userId, List<String> roleIdList) {
        if (CollUtil.isEmpty(roleIdList)) {
            return CollUtil.newArrayList();
        }
        return SpringContextHolder.getBean(RelationUserRoleMapper.class).selectList(Wrappers.<RelationUserRoleDO>lambdaQuery()
                .eq(RelationUserRoleDO::getUserId, userId)
                .in(RelationUserRoleDO::getRoleId, roleIdList));
    }

}
