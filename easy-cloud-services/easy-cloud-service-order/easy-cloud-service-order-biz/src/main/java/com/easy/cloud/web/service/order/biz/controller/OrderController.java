package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.order.api.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import io.swagger.annotations.ApiOperation;
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
 * Order API
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Slf4j
@RestController
@RequestMapping(value = "order")
public class OrderController {

  @Autowired
  private IOrderService orderService;

  /**
   * 新增
   *
   * @param orderDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  public Object save(@Validated @RequestBody OrderDTO orderDTO) {
    return orderService.save(orderDTO);
  }

  /**
   * 更新
   *
   * @param orderDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  public Object update(@Validated @RequestBody OrderDTO orderDTO) {
    return orderService.update(orderDTO);
  }

  /**
   * 根据ID移除数据
   *
   * @param orderId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{orderId}")
  public Object removeById(@PathVariable @NotNull(message = "当前ID不能为空") Long orderId) {
    return orderService.removeById(orderId);
  }

  /**
   * 根据ID获取详情
   *
   * @param orderId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{orderId}")
  public Object detailById(@PathVariable @NotNull(message = "当前ID不能为空") Long orderId) {
    return orderService.detailById(orderId);
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  public Object list() {
    return orderService.list();
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
    return orderService.page(page, size);
  }


  /**
   * 支付成功回调
   *
   * @param orderNo 订单编号
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
   */
  @PostMapping("pay/success/handler/{orderNo}")
  @ApiOperation(value = "支付成功回调")
  public HttpResult<Boolean> paySuccessHandler(@PathVariable String orderNo) {
    return HttpResult.ok(orderService.paySuccessHandler(orderNo));
  }
}