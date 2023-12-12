package com.easy.cloud.web.module.sms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.sms.api.dto.SmsDTO;
import com.easy.cloud.web.module.sms.api.vo.SmsVO;
import com.easy.cloud.web.module.sms.biz.service.ISmsService;
import java.util.List;
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
 * Sms API
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Slf4j
@RestController
@RequestMapping(value = "sms")
public class SmsController {

  @Autowired
  private ISmsService smsService;

  /**
   * 新增
   *
   * @param smsDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  public HttpResult<SmsVO> save(@Validated @RequestBody SmsDTO smsDTO) {
    return HttpResult.ok(smsService.save(smsDTO));
  }

  /**
   * 更新
   *
   * @param smsDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  public HttpResult<SmsVO> update(@Validated @RequestBody SmsDTO smsDTO) {
    return HttpResult.ok(smsService.update(smsDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param smsId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{smsId}")
  public HttpResult<Boolean> removeById(@PathVariable @NotNull(message = "当前ID不能为空") String smsId) {
    return HttpResult.ok(smsService.removeById(smsId));
  }

  /**
   * 根据ID获取详情
   *
   * @param smsId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{smsId}")
  public HttpResult<SmsVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String smsId) {
    return HttpResult.ok(smsService.detailById(smsId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  public HttpResult<List<SmsVO>> list() {
    return HttpResult.ok(smsService.list());
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  public HttpResult<Page<SmsVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(smsService.page(page, size));
  }
}