package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import com.easy.cloud.web.service.upms.biz.domain.db.RelationUserPermissionDO;
import com.easy.cloud.web.service.upms.biz.mapper.RelationUserPermissionMapper;

import java.util.List;

/**
 * 用户权限业务逻辑关系
 * 注：某些业务场景下，需对某些特殊的人权赋予特殊的权限，此权限与角色无关
 *
 * @author GR
 * @date 2020-11-4 15:17
 */
public interface IRelationUserPermissionService {

    /**
     * 保存角色权限
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     */
    default void saveRelationUserPermission(String userId, String permissionId) {
        Assert.notBlank(userId, "保存用户权限时，用户ID为空");
        Assert.notBlank(permissionId, "保存用户权限时，权限ID为空");
        RelationUserPermissionDO relationUserPermissionDO = RelationUserPermissionDO.builder().userId(userId).permissionId(permissionId)
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build();
        SpringContextHolder.getBean(RelationUserPermissionMapper.class).insert(relationUserPermissionDO);
    }

    /**
     * 保存角色权限
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     * @param actions      操作ID
     */
    default void saveRelationUserPermission(String userId, String permissionId, List<PermissionActionBO> actions) {
        Assert.notBlank(userId, "保存用户权限时，用户ID为空");
        Assert.notBlank(permissionId, "保存用户权限时，权限ID为空");
        RelationUserPermissionDO relationUserPermissionDO = RelationUserPermissionDO.builder().userId(userId).permissionId(permissionId)
                // 默认的权限操作
                .actions(actions)
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build();
        SpringContextHolder.getBean(RelationUserPermissionMapper.class).insert(relationUserPermissionDO);
    }

    /**
     * 移除角色相关权限，不指定角色
     *
     * @param permissionId 权限ID
     */
    default void removeRelationUserPermissionByPermissionId(String permissionId) {
        SpringContextHolder.getBean(RelationUserPermissionMapper.class).delete(Wrappers.<RelationUserPermissionDO>lambdaQuery().eq(RelationUserPermissionDO::getPermissionId, permissionId));
    }

    /**
     * 根据角色查询权限
     *
     * @param userId           用户ID
     * @param permissionIdList 权限ID集合
     * @return java.util<com.easy.cloud.web.upms.biz.domain.db.RelationUserPermissionDO>
     */
    default List<RelationUserPermissionDO> findRelationUserPermissionByUserIdAndInPermissionId(String userId, List<String> permissionIdList) {
        if (CollUtil.isEmpty(permissionIdList)) {
            return CollUtil.newArrayList();
        }
        return SpringContextHolder.getBean(RelationUserPermissionMapper.class).selectList(Wrappers.<RelationUserPermissionDO>lambdaQuery()
                .eq(RelationUserPermissionDO::getUserId, userId)
                .in(RelationUserPermissionDO::getPermissionId, permissionIdList));
    }

}
