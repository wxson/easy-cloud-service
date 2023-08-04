package com.easy.cloud.web.service.upms.biz.controller;

import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import com.easy.cloud.web.service.upms.biz.constant.UpmsConstants;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
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
  @PreAuthorize("@pms.hasPermission('menu_add')")
  public Object save(@Validated @RequestBody MenuDTO menuDTO) {
    return HttpResult.ok(menuService.save(menuDTO));
  }

  /**
   * 更新
   *
   * @param menuDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('menu_edit')")
  public Object update(@Validated @RequestBody MenuDTO menuDTO) {
    return HttpResult.ok(menuService.update(menuDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param menuId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{menuId}")
  @PreAuthorize("@pms.hasPermission('menu_delete')")
  public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") Long menuId) {
    return HttpResult.ok(menuService.removeById(menuId));
  }

  /**
   * 根据ID获取详情
   *
   * @param menuId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{menuId}")
  public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") Long menuId) {
    return HttpResult.ok(menuService.detailById(menuId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  public Object list() {
    return HttpResult.ok(menuService.list());
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  public Object page(@RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(menuService.page(page, size));
  }

  /**
   * 获取当前用户菜单列表
   */
  @GetMapping(value = "tree")
  @ApiOperation(value = "获取当前用户菜单列表")
  public HttpResult<List<Tree<Long>>> findUserMenus(MenuTypeEnum type, Long parentId) {
    // 获取当前登录用户的角色ID
    List<Long> userRoles = SecurityUtils.getUserRoles();
    // 默认更目录
    parentId = Objects.isNull(parentId) ? UpmsConstants.MENU_TREE_ROOT_ID : parentId;
    // 测试查询超管下的所有菜单
    return HttpResult.ok(menuService.findUserMenus(type, parentId, userRoles));
  }


  /**
   * 获取当前角色菜单列表
   */
  @GetMapping(value = "tree/{role}")
  @ApiOperation(value = "获取当前角色菜单列表")
  public HttpResult<List<Tree<Long>>> findMenusByRole(MenuTypeEnum type, Long parentId, Long role) {
    // 默认更目录
    parentId = Objects.isNull(parentId) ? UpmsConstants.MENU_TREE_ROOT_ID : parentId;
    // 测试查询超管下的所有菜单
    return HttpResult.ok(menuService.findUserMenus(type, parentId, Lists.newArrayList(role)));
  }
}