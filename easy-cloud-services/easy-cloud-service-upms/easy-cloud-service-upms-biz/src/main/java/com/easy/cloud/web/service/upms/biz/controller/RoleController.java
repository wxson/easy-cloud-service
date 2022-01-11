package com.easy.cloud.web.service.upms.biz.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.upms.biz.domain.db.RoleDO;
import com.easy.cloud.web.service.upms.biz.domain.dto.RoleDTO;
import com.easy.cloud.web.service.upms.biz.domain.query.RoleQuery;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fast Java
 * @date 2021-04-01
 */
@Slf4j
@RestController
@RequestMapping("role")
@AllArgsConstructor
@Api(value = "角色管理", tags = "角色管理")
public class RoleController extends BaseController<RoleQuery, RoleDTO, RoleDO> {

    private final IRoleService roleService;

    @Override
    public IRepositoryService<RoleDO> getService() {
        return roleService;
    }

    /**
     * 重写角色存储逻辑：存储角色时，可顺带存储当前角色分配的权限
     *
     * @param dto 角色信息
     * @return reactor.core.publisher.Mono<com.easy.cloud.web.common.response.HttpResponse>
     */
    @Override
    public HttpResult<Boolean> save(@Validated @RequestBody RoleDTO dto) {
        RoleDO roleDO = dto.convert();
        this.getService().save(roleDO);
        if (CollUtil.isNotEmpty(dto.getPermissionList())) {
            dto.getPermissionList().forEach(relationRolePermissionDO -> roleService.saveRelationRolePermission(roleDO.getId(),
                    relationRolePermissionDO.getPermissionId(), relationRolePermissionDO.getActions()));
        }
        return HttpResult.ok(true);
    }

    /**
     * 分配权限：暂时采取方案一
     * 方案一：全部删除之前的角色权限信息，再统一添加新的权限信息
     * 方案二：货权原先的权限与新的权限进行对比，缺少的删除，多出来的新增，相同的保留（业务逻辑相对复杂些）
     *
     * @param dto 角色全新信息
     * @return reactor.core.publisher.Mono<com.easy.cloud.web.common.response.HttpResponse>
     */
    @PostMapping("allot/permissions")
    @ApiOperation(value = "分配权限")
    public HttpResult<Boolean> allotRolePermissionList(@Validated @RequestBody RoleDTO dto) {
        if (StrUtil.isBlank(dto.getId())) {
            throw new BusinessException("当前角色ID不能为空");
        }

        RoleDO roleDO = roleService.getOne(Wrappers.<RoleDO>lambdaQuery().eq(RoleDO::getId, dto.getId()));
        if (null == roleDO) {
            throw new BusinessException("当前角色信息不存在");
        }

        // 当前分配权限不为空
        if (CollUtil.isNotEmpty(dto.getPermissionList())) {
            dto.getPermissionList().forEach(relationRolePermissionDO -> {
                // 移除当前角色下的所有权限
                roleService.removeRelationRolePermissionByRoleId(dto.getId());
                // 当前权限ID不为空
                if (StrUtil.isNotBlank(relationRolePermissionDO.getPermissionId())) {
                    roleService.saveRelationRolePermission(roleDO.getId(),
                            relationRolePermissionDO.getPermissionId(), relationRolePermissionDO.getActions());
                }
            });
        }
        return HttpResult.ok();
    }
}