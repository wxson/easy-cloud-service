package com.easy.cloud.web.service.upms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.log.annotation.OperationLog;
import com.easy.cloud.web.component.log.annotation.OperationLog.Action;
import com.easy.cloud.web.service.upms.api.dto.DepartmentDTO;
import com.easy.cloud.web.service.upms.biz.service.IDepartmentService;
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
 * Department API
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Slf4j
@RestController
@RequestMapping(value = "department")
public class DepartmentController {

  @Autowired
  private IDepartmentService departmentService;

  /**
   * 新增
   *
   * @param departmentDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('department_add')")
  @OperationLog(value = "新增部门", action = Action.ADD)
  public Object save(@Validated @RequestBody DepartmentDTO departmentDTO) {
    return HttpResult.ok(departmentService.save(departmentDTO));
  }

  /**
   * 更新
   *
   * @param departmentDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('department_edit')")
  @OperationLog(value = "更新部门", action = Action.UPDATE)
  public Object update(@Validated @RequestBody DepartmentDTO departmentDTO) {
    return HttpResult.ok(departmentService.update(departmentDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param departmentId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{departmentId}")
  @PreAuthorize("@pms.hasPermission('department_delete')")
  @OperationLog(value = "移除部门", action = Action.DELETE)
  public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") Long departmentId) {
    return HttpResult.ok(departmentService.removeById(departmentId));
  }

  /**
   * 根据ID获取详情
   *
   * @param departmentId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{departmentId}")
  @OperationLog(value = "部门详情", action = Action.FIND)
  public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") Long departmentId) {
    return HttpResult.ok(departmentService.detailById(departmentId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  public Object list() {
    return HttpResult.ok(departmentService.list());
  }


  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "tree")
  public Object tree() {
    return HttpResult.ok(departmentService.tree());
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
    return HttpResult.ok(departmentService.page(page, size));
  }
}