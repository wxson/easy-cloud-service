package com.easy.cloud.web.service.upms.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.db.RoleDO;
import com.easy.cloud.web.service.upms.biz.mapper.RoleMapper;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Role 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-04-01
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements IRoleService {

    @Override
    public RoleDO verifyBeforeSave(RoleDO roleDO) {
        // 角色名称是否重复，角色创建只能有某些角色有权限，非所有用户,鉴别条件：角色名、租户ID
        RoleDO existRoleDO = this.getOne(Wrappers.<RoleDO>lambdaQuery()
                .eq(RoleDO::getTenantId, SecurityUtils.getAuthenticationUser().getTenantId())
                .eq(RoleDO::getName, roleDO.getName()));
        if (null != existRoleDO) {
            throw new BusinessException("您所创建的角色名称已存在");
        }

        return roleDO;
    }

    @Override
    public void verifyAfterDelete(Serializable roleId) {
        // 删除角色之后，移除该角色的所有相关权限
        this.removeRelationRolePermissionByRoleId(roleId);
        // 删除角色之后，移除该角色的所有相关用户
        this.removeRelationUserRoleByRoleId(roleId);
    }

    @Override
    public void initDefaultConfiguration() {
//        MongoIterable<String> mongoIterable = this.getMongoTemplate().getDb().listCollectionNames();
//        ArrayList<String> mongoTableNameList = CollUtil.newArrayList(mongoIterable);
//        Document annotation = RoleDO.class.getAnnotation(Document.class);
//        if (Objects.nonNull(annotation) && !mongoTableNameList.contains(annotation.value())) {
//            log.info("-------------初始化系统默认角色信息表--------------");
//            // 初始化超级管理员角色,最顶级角色
//            RoleDO superAdminRoleDO = this.save(RoleDO.builder()
//                    .id(RoleEnum.ROLE_SUPER_ADMIN.getId())
//                    .name(RoleEnum.ROLE_SUPER_ADMIN.getDescribe())
//                    .tenantId("system_default")
//                    .creatorAt("system_default")
//                    .build());
//            // 初始化其他角色
//            for (RoleEnum roleEnum : RoleEnum.values()) {
//                if (RoleEnum.ROLE_SUPER_ADMIN == roleEnum) {
//                    continue;
//                }
//
//                this.save(RoleDO.builder()
//                        .id(roleEnum.getId())
//                        .name(roleEnum.getDescribe())
//                        .tenantId(superAdminRoleDO.getTenantId())
//                        .creatorAt(superAdminRoleDO.getId())
//                        .build());
//            }
//        }
    }
}