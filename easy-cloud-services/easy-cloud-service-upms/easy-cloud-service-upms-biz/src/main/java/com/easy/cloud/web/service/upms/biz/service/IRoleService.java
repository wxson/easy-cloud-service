package com.easy.cloud.web.service.upms.biz.service;


import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.upms.biz.domain.db.RoleDO;

/**
 * Role 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-04-01
 */
public interface IRoleService extends IRepositoryService<RoleDO>, IRelationRolePermissionService, IRelationUserRoleService, IInitService {

}