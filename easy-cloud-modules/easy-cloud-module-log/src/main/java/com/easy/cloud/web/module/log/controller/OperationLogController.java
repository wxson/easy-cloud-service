package com.easy.cloud.web.module.log.controller;

import com.easy.cloud.web.module.log.service.IOperationLogService;
import com.easy.cloud.web.service.upms.api.dto.OperationLogDTO;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * OperationLog API
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Slf4j
@RestController
@RequestMapping(value = "operationLog")
public class OperationLogController {

  @Autowired
  private IOperationLogService operationLogService;

  /**
   * 新增
   *
   * @param operationLogDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  public Object save(@Validated @RequestBody OperationLogDTO operationLogDTO) {
    return operationLogService.save(operationLogDTO);
  }

  /**
   * 更新
   *
   * @param operationLogDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  public Object update(@Validated @RequestBody OperationLogDTO operationLogDTO) {
    return operationLogService.update(operationLogDTO);
  }

  /**
   * 根据ID移除数据
   *
   * @param operationLogId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{operationLogId}")
  public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") Long operationLogId) {
    return operationLogService.removeById(operationLogId);
  }

  /**
   * 根据ID获取详情
   *
   * @param operationLogId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{operationLogId}")
  public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") Long operationLogId) {
    return operationLogService.detailById(operationLogId);
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  public Object list() {
    return operationLogService.list();
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
    return operationLogService.page(page, size);
  }
}