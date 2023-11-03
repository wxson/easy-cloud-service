package com.easy.cloud.web.module.dict.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.dict.api.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.biz.service.IDictItemService;
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
  @PreAuthorize("@pms.hasPermission('system:dict:add')")
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
  @PreAuthorize("@pms.hasPermission('system:dict:edit')")
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
  @PreAuthorize("@pms.hasPermission('system:dict:delete')")
  public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") String dictItemId) {
    return HttpResult.ok(dictItemService.removeById(dictItemId));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list/{dictType}")
  @PreAuthorize("@pms.hasPermission('system:dict:query')")
  public Object list(@PathVariable String dictType) {
    return HttpResult.ok(dictItemService.list(dictType));
  }
}