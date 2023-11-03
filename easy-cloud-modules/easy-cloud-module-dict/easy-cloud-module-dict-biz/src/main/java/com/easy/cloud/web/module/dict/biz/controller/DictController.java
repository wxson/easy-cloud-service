package com.easy.cloud.web.module.dict.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.dict.api.dto.DictDTO;
import com.easy.cloud.web.module.dict.api.vo.DictVO;
import com.easy.cloud.web.module.dict.biz.service.IDictService;
import java.util.List;
import javax.validation.constraints.NotNull;
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
 * Dict API
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Slf4j
@RestController
@RequestMapping(value = "dict")
public class DictController {

  @Autowired
  private IDictService dictService;

  /**
   * 新增
   *
   * @param dictDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('system:dict:add')")
  public HttpResult<DictVO> save(@Validated @RequestBody DictDTO dictDTO) {
    return HttpResult.ok(dictService.save(dictDTO));
  }

  /**
   * 更新
   *
   * @param dictDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('system:dict:edit')")
  public HttpResult<DictVO> update(@Validated @RequestBody DictDTO dictDTO) {
    return HttpResult.ok(dictService.update(dictDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param dictId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{dictId}")
  @PreAuthorize("@pms.hasPermission('system:dict:delete')")
  public HttpResult<Boolean> removeById(
      @PathVariable @NotNull(message = "当前ID不能为空") String dictId) {
    return HttpResult.ok(dictService.removeById(dictId));
  }

  /**
   * 根据ID获取详情
   *
   * @param dictId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{dictId}")
  @PreAuthorize("@pms.hasPermission('system:dict:query')")
  public HttpResult<DictVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String dictId) {
    return HttpResult.ok(dictService.detailById(dictId));
  }

  /**
   * 根据类型获取详情
   *
   * @param dictType 类型
   * @return 详情数据
   */
  @GetMapping(value = "type/{dictType}")
  @PreAuthorize("@pms.hasPermission('system:dict:query')")
  public HttpResult<DictVO> detailByDictType(
      @PathVariable @NotNull(message = "当前字典类型不能为空") String dictType) {
    return HttpResult.ok(dictService.detailByType(dictType));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  @PreAuthorize("@pms.hasPermission('system:dict:query')")
  public HttpResult<List<DictVO>> list() {
    return HttpResult.ok(dictService.list());
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  @PreAuthorize("@pms.hasPermission('system:dict:query')")
  public HttpResult<Page<DictVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(dictService.page(page, size));
  }
}