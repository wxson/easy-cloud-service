package com.easy.cloud.web.service.upms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.db.MenuDO;
import com.easy.cloud.web.service.upms.biz.domain.dto.MenuDTO;
import com.easy.cloud.web.service.upms.biz.domain.query.MenuQuery;
import com.easy.cloud.web.service.upms.biz.domain.vo.MenuVO;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单API
 *
 * @author GR
 * @date 2020-11-4 15:02
 */
@RestController
@RequestMapping("menu")
@AllArgsConstructor
@Api(value = "菜单管理", tags = "菜单管理")
public class MenuController extends BaseController<MenuQuery, MenuDTO, MenuDO> {

    private final IMenuService menuService;

    @Override
    public IRepositoryService<MenuDO> getService() {
        return menuService;
    }

    /**
     * 查询当前登录用户权限下的所有菜单
     */
    @GetMapping("list")
    @ApiOperation(value = "获取当前用户菜单列表")
    public HttpResult<List<MenuVO>> findUserMenus() {
        // 获取当前登录用户的角色ID
        String channelId = SecurityUtils.getAuthenticationUser().getChannelId();
        // 测试查询超管下的所有菜单
        return HttpResult.ok(menuService.findMenusByRoleId(channelId));
    }

    /**
     * 移动菜单位置
     *
     * @param parentId 父级目录ID
     * @param menuId   当前菜单ID
     */
    @PostMapping("move/{parentId}/{menuId}")
    @ApiOperation(value = "移动菜单位置")
    public HttpResult<Boolean> moveMenuPosition(@PathVariable String parentId, @PathVariable String menuId) {
        menuService.moveMenuPosition(parentId, menuId);
        return HttpResult.ok();
    }
}
