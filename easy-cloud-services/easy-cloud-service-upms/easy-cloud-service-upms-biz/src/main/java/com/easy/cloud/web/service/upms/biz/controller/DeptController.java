package com.easy.cloud.web.service.upms.biz.controller;

import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.log.annotation.SysLog;
import com.easy.cloud.web.component.log.annotation.SysLog.Action;
import com.easy.cloud.web.service.upms.api.dto.DeptDTO;
import com.easy.cloud.web.service.upms.api.vo.DeptVO;
import com.easy.cloud.web.service.upms.biz.service.IDeptService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping(value = "dept")
public class DeptController {

  @Autowired
  private IDeptService deptService;

  /**
   * 新增
   *
   * @param deptDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('system:dept:add')")
  @SysLog(value = "部门新增", action = Action.ADD)
  @ApiOperation(value = "部门新增")
  public HttpResult<DeptVO> save(@Validated @RequestBody DeptDTO deptDTO) {
    return HttpResult.ok(deptService.save(deptDTO));
  }

  /**
   * 更新
   *
   * @param deptDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('system:dept:update')")
  @SysLog(value = "部门更新", action = Action.UPDATE)
  @ApiOperation(value = "部门更新")
  public HttpResult<DeptVO> update(@Validated @RequestBody DeptDTO deptDTO) {
    return HttpResult.ok(deptService.update(deptDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param deptId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{deptId}")
  @PreAuthorize("@pms.hasPermission('system:dept:delete')")
  @SysLog(value = "部门删除", action = Action.DELETE)
  @ApiOperation(value = "部门删除")
  public HttpResult<Boolean> removeById(
      @PathVariable @NotBlank(message = "当前ID不能为空") String deptId) {
    return HttpResult.ok(deptService.removeById(deptId));
  }

  /**
   * 根据ID获取详情
   *
   * @param deptId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{deptId}")
  @PreAuthorize("@pms.hasPermission('system:dept:query')")
  @SysLog(value = "部门详情", action = Action.FIND)
  @ApiOperation(value = "部门详情")
  public HttpResult<DeptVO> detailById(
      @PathVariable @NotBlank(message = "当前ID不能为空") String deptId) {
    return HttpResult.ok(deptService.detailById(deptId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  @PreAuthorize("@pms.hasPermission('system:dept:query')")
  @SysLog(value = "部门列表", action = Action.FIND)
  @ApiOperation(value = "部门列表")
  public HttpResult<List<DeptVO>> list() {
    return HttpResult.ok(deptService.list());
  }


  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "tree")
  @PreAuthorize("@pms.hasPermission('system:dept:query')")
  @SysLog(value = "部门树", action = Action.FIND)
  @ApiOperation(value = "部门树")
  public HttpResult<List<Tree<String>>> tree() {
    return HttpResult.ok(deptService.tree());
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  @PreAuthorize("@pms.hasPermission('system:dept:query')")
  @SysLog(value = "部门分页", action = Action.FIND)
  @ApiOperation(value = "部门分页")
  public HttpResult<Page<DeptVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(deptService.page(page, size));
  }
}