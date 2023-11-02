package com.easy.cloud.web.service.upms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.log.annotation.SysLog;
import com.easy.cloud.web.component.log.annotation.SysLog.Action;
import com.easy.cloud.web.service.upms.api.dto.RoleDTO;
import com.easy.cloud.web.service.upms.api.dto.RoleMenuDTO;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import io.swagger.annotations.ApiOperation;
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
 * Role API
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Slf4j
@RestController
@RequestMapping(value = "role")
public class RoleController {

  @Autowired
  private IRoleService roleService;

  /**
   * 新增
   *
   * @param roleDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('system:role:add')")
  @SysLog(value = "角色新增", action = Action.ADD)
  @ApiOperation(value = "角色新增")
  public Object save(@Validated @RequestBody RoleDTO roleDTO) {
    return HttpResult.ok(roleService.save(roleDTO));
  }

  /**
   * 更新
   *
   * @param roleDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('system:role:update')")
  @SysLog(value = "角色更新", action = Action.UPDATE)
  @ApiOperation(value = "角色更新")
  public Object update(@Validated @RequestBody RoleDTO roleDTO) {
    return HttpResult.ok(roleService.update(roleDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param roleId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{roleId}")
  @PreAuthorize("@pms.hasPermission('system:role:delete')")
  @SysLog(value = "角色删除", action = Action.DELETE)
  @ApiOperation(value = "角色删除")
  public Object removeById(@PathVariable @NotBlank(message = "当前ID不能为空") String roleId) {
    return HttpResult.ok(roleService.removeById(roleId));
  }

  /**
   * 根据ID获取详情
   *
   * @param roleId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{roleId}")
  @PreAuthorize("@pms.hasPermission('system:role:query')")
  @SysLog(value = "角色详情", action = Action.FIND)
  @ApiOperation(value = "角色详情")
  public Object detailById(@PathVariable @NotBlank(message = "当前ID不能为空") String roleId) {
    return HttpResult.ok(roleService.detailById(roleId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  @PreAuthorize("@pms.hasPermission('system:role:query')")
  @SysLog(value = "角色列表", action = Action.FIND)
  @ApiOperation(value = "角色列表")
  public Object list() {
    return HttpResult.ok(roleService.list());
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  @PreAuthorize("@pms.hasPermission('system:role:query')")
  @SysLog(value = "角色分页", action = Action.FIND)
  @ApiOperation(value = "角色分页")
  public Object page(@RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false) String name) {
    return HttpResult.ok(roleService.page(page, size));
  }


  /**
   * 绑定权限
   *
   * @param roleMenuDTO 角色信息
   * @return success/false
   */
  @PostMapping("/bind/permission")
  @SysLog(value = "绑定权限", action = Action.UPDATE)
  @ApiOperation(value = "绑定权限")
  public HttpResult<RoleVO> bindRolePermission(@RequestBody RoleMenuDTO roleMenuDTO) {
    return HttpResult.ok(roleService.bindRoleMenu(roleMenuDTO));
  }
}