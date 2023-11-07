package com.easy.cloud.web.module.log.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.log.api.dto.SysLogDTO;
import com.easy.cloud.web.module.log.api.vo.SysLogVO;
import com.easy.cloud.web.module.log.biz.service.ISysLogService;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SysLog API
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Slf4j
@RestController
@RequestMapping(value = "sys/log")
public class SysLogController {

  @Autowired
  private ISysLogService sysLogService;

  /**
   * 新增
   *
   * @param sysLogDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @ApiOperation(value = "日志存储")
  public HttpResult<SysLogVO> save(@Validated @RequestBody SysLogDTO sysLogDTO) {
    return HttpResult.ok(sysLogService.save(sysLogDTO));
  }

  /**
   * 根据ID获取详情
   *
   * @param sysLogId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{sysLogId}")
  public HttpResult<SysLogVO> detailById(
      @PathVariable @NotNull(message = "当前ID不能为空") String sysLogId) {
    return HttpResult.ok(sysLogService.detailById(sysLogId));
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  public HttpResult<Page<SysLogVO>> page(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(sysLogService.page(page, size));
  }
}