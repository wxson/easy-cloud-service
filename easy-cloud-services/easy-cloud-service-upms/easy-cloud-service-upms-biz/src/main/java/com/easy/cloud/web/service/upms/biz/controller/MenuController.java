package com.easy.cloud.web.service.upms.biz.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.log.annotation.SysLog;
import com.easy.cloud.web.component.log.annotation.SysLog.Action;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Menu API
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@Slf4j
@RestController
@RequestMapping(value = "menu")
public class MenuController {

  @Autowired
  private IMenuService menuService;

  /**
   * 新增
   *
   * @param menuDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('system:menu:add')")
  @SysLog(value = "菜单新增", action = Action.ADD)
  @ApiOperation(value = "菜单菜单")
  public HttpResult<MenuVO> save(@Validated @RequestBody MenuDTO menuDTO) {
    log.info("新增菜单：{}", menuDTO);
    return HttpResult.ok(menuService.save(menuDTO));
  }

  /**
   * 更新
   *
   * @param menuDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('system:menu:update')")
  @SysLog(value = "菜单更新", action = Action.UPDATE)
  @ApiOperation(value = "菜单更新")
  public HttpResult<MenuVO> update(@Validated @RequestBody MenuDTO menuDTO) {
    return HttpResult.ok(menuService.update(menuDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param menuId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{menuId}")
  @PreAuthorize("@pms.hasPermission('system:menu:delete')")
  @SysLog(value = "菜单删除", action = Action.DELETE)
  @ApiOperation(value = "菜单删除")
  public HttpResult<Boolean> removeById(
      @PathVariable @NotBlank(message = "当前ID不能为空") String menuId) {
    return HttpResult.ok(menuService.removeById(menuId));
  }

  /**
   * 根据ID获取详情
   *
   * @param menuId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{menuId}")
  @PreAuthorize("@pms.hasPermission('system:menu:query')")
  @SysLog(value = "菜单详情", action = Action.FIND)
  @ApiOperation(value = "菜单详情")
  public HttpResult<MenuVO> detailById(
      @PathVariable @NotBlank(message = "当前ID不能为空") String menuId) {
    return HttpResult.ok(menuService.detailById(menuId));
  }

  /**
   * 获取当前用户菜单列表
   */
  @GetMapping(value = "tree")
  @PreAuthorize("@pms.hasPermission('system:menu:query')")
  @SysLog(value = "菜单树", action = Action.FIND)
  @ApiOperation(value = "菜单树")
  public HttpResult<List<Tree<String>>> findUserMenus(
      @RequestParam(defaultValue = GlobalCommonConstants.DEPART_TREE_ROOT_ID) String parentId) {
    // 测试查询超管下的所有菜单
    return HttpResult.ok(menuService.findUserMenus(parentId,
        CollUtil.newArrayList(SecurityUtils.getAuthenticationUser().getChannel())));
  }


  /**
   * 获取当前用户菜单列表
   */
  @GetMapping(value = "role/{roleId}")
  @PreAuthorize("@pms.hasPermission('system:menu:query')")
  @SysLog(value = "角色菜单列表", action = Action.FIND)
  @ApiOperation(value = "角色菜单列表")
  public HttpResult<List<String>> findRoleMenus(@PathVariable String roleId) {
    return HttpResult.ok(menuService.findRoleMenus(roleId));
  }
}