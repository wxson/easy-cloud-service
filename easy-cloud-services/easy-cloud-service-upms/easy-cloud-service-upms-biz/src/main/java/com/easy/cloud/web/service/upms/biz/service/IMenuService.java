package com.easy.cloud.web.service.upms.biz.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.service.upms.biz.domain.db.MenuDO;
import com.easy.cloud.web.service.upms.biz.domain.vo.MenuVO;

import java.util.List;

/**
 * @author GR
 * @date 2020-11-4 15:17
 */
public interface IMenuService extends IRepositoryService<MenuDO>, IPermissionService, IInitService {

    /**
     * 获取所有的菜单信息
     *
     * @return java.lang.Object
     */
    List<MenuVO> findAllTreeMenus();

    /**
     * 根据当前用户ID获取所有的菜单信息
     *
     * @param userId 用户ID
     * @return java.lang.Object
     */
    List<MenuVO> findMenusByUserId(String userId);

    /**
     * 根据当前用户角色ID获取所有的菜单信息
     *
     * @param roleId 角色ID
     * @return java.lang.Object
     */
    List<MenuVO> findMenusByRoleId(String roleId);

    /**
     * 移动菜单位置
     *
     * @param parentId 菜单父级目录ID
     * @param menuId   菜单ID
     * @return com.easy。cloud.web.permission.module.domain.vo.MenuVO
     */
    MenuVO moveMenuPosition(String parentId, String menuId);
}
