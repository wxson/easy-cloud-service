package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.bo.PermissionActionBO;
import com.easy.cloud.web.service.upms.biz.domain.db.PermissionDO;
import com.easy.cloud.web.service.upms.biz.domain.db.RelationRolePermissionDO;
import com.easy.cloud.web.service.upms.biz.enums.PermissionActionEnum;
import com.easy.cloud.web.service.upms.biz.enums.PermissionTypeEnum;
import com.easy.cloud.web.service.upms.biz.enums.RoleEnum;
import com.easy.cloud.web.service.upms.biz.mapper.PermissionMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author GR
 * @date 2020-11-4 15:17
 */
public interface IPermissionService extends IRelationRolePermissionService, IRelationUserPermissionService {

    /**
     * 保存权限
     *
     * @param targetId              对应的权限ID
     * @param permissionTypeEnum    权限类型
     * @param permissionActionEnums 用户自定义action
     */
    default void savePermission(String targetId, PermissionTypeEnum permissionTypeEnum, PermissionActionEnum... permissionActionEnums) {
        Assert.notBlank(targetId);
        PermissionDO permissionDO = PermissionDO.builder().targetId(targetId).type(permissionTypeEnum.getCode())
                .creatorAt(SecurityUtils.getAuthenticationUser().getId()).createAt(DateUtil.now()).build();
        SpringContextHolder.getBean(PermissionMapper.class).insert(permissionDO);
        // 非免费权限，不设置任何角色，超管也不行
        if (!permissionTypeEnum.getFree()) {
            return;
        }

        // 赋值超级管理员权限,切当前权限支持action操作
        if (permissionTypeEnum.getSupportAction()) {
            this.saveRelationRolePermission(RoleEnum.ROLE_SUPER_ADMIN.getId(), permissionDO.getId(), PermissionActionBO.defaultPermissionActions(permissionActionEnums));
            return;
        }

        // 赋值超级管理员权限
        this.saveRelationRolePermission(RoleEnum.ROLE_SUPER_ADMIN.getId(), permissionDO.getId());
    }

    /**
     * 移除权限
     *
     * @param targetId 移除的权限ID
     */
    default void removeRelationPermission(Serializable targetId) {
        // 查询当前菜单、应用等关联的权限实体对象
        Optional<PermissionDO> permissionDoOptional = Optional.ofNullable(SpringContextHolder.getBean(PermissionMapper.class).selectOne(Wrappers.<PermissionDO>lambdaQuery().eq(PermissionDO::getTargetId, targetId)));
        if (permissionDoOptional.isPresent()) {
            PermissionDO permissionDO = permissionDoOptional.get();
            // 移除与角色相关权限数据
            this.removeRelationRolePermissionByPermissionId(permissionDO.getId());
            // 移除与用户相关权限数据
            this.removeRelationUserPermissionByPermissionId(permissionDO.getId());
            // 移除权限本身
            SpringContextHolder.getBean(PermissionMapper.class).deleteById(permissionDO.getId());
        }

    }

    /**
     * 根据权限类型查询权限信息
     *
     * @param permissionTypeEnum 权限类型
     */
    default List<PermissionDO> findPermissionByType(PermissionTypeEnum permissionTypeEnum) {
        return SpringContextHolder.getBean(PermissionMapper.class).selectList(Wrappers.<PermissionDO>lambdaQuery().eq(PermissionDO::getType, permissionTypeEnum));
    }

    /**
     * 根据角色查询权限
     *
     * @param roleId             角色ID
     * @param permissionTypeEnum 权限类型
     * @return void
     */
    default List<PermissionDO> findPermissionByRoleIdAndType(String roleId, PermissionTypeEnum permissionTypeEnum) {
        // 获取该类型下的所有权限
        List<PermissionDO> permissionDOList = this.findPermissionByType(permissionTypeEnum);
        // 获取当前角色的所有权限列表
        Map<String, RelationRolePermissionDO> relationRolePermissionDoMap = this.findRelationRolePermissionByRoleIdAndInPermissionId(roleId,
                permissionDOList.stream().map(PermissionDO::getId).collect(Collectors.toList())).stream().collect(Collectors.toMap(RelationRolePermissionDO::getPermissionId, item -> item));
        Set<String> keySet = relationRolePermissionDoMap.keySet();
        return permissionDOList.stream()
                // 过滤掉不包含的权限
                .filter(permissionDO -> keySet.contains(permissionDO.getId()))
                // 设置actions
                .peek(permissionDO -> Optional.ofNullable(relationRolePermissionDoMap.get(permissionDO.getId())).ifPresent(relationRolePermissionDO -> permissionDO.setActions(relationRolePermissionDO.getActions())))
                .collect(Collectors.toList());
    }
}
