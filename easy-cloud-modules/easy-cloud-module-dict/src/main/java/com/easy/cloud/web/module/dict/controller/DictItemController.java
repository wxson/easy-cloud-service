package com.easy.cloud.web.module.dict.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.dict.domain.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.service.IDictItemService;
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
 * DictItem API
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
@Slf4j
@RestController
@RequestMapping(value = "dictItem")
public class DictItemController {

  @Autowired
  private IDictItemService dictItemService;

  /**
   * 新增
   *
   * @param dictItemDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('dict_item_add')")
  public Object save(@Validated @RequestBody DictItemDTO dictItemDTO) {
    return HttpResult.ok(dictItemService.save(dictItemDTO));
  }

  /**
   * 更新
   *
   * @param dictItemDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('dict_item_edit')")
  public Object update(@Validated @RequestBody DictItemDTO dictItemDTO) {
    return HttpResult.ok(dictItemService.update(dictItemDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param dictItemId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{dictItemId}")
  @PreAuthorize("@pms.hasPermission('dict_item_delete')")
  public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") Long dictItemId) {
    return HttpResult.ok(dictItemService.removeById(dictItemId));
  }

  /**
   * 根据ID获取详情
   *
   * @param dictItemId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{dictItemId}")
  public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") Long dictItemId) {
    return HttpResult.ok(dictItemService.detailById(dictItemId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  public Object list() {
    return HttpResult.ok(dictItemService.list());
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
    return HttpResult.ok(dictItemService.page(page, size));
  }
}