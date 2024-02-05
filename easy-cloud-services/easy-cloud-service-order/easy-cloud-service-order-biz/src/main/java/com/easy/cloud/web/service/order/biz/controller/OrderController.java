package com.easy.cloud.web.service.order.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.api.dto.OrderQueryDTO;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Order Admin API
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Slf4j
@RestController
@Tag(name = "Order", description = "订单管理")
public class OrderController {

    @Autowired
    private IOrderService orderService;


    /**
     * 创建订单
     *
     * @param orderCreateDTO 创建订单信息
     * @return 新增数据
     */
    @PostMapping(value = "create")
    @ApiOperation(value = "创建订单")
    public HttpResult<OrderVO> createOrder(@Validated @RequestBody OrderCreateDTO orderCreateDTO) {
        return HttpResult.ok(orderService.createOrder(orderCreateDTO));
    }

    /**
     * 根据ID获取详情
     *
     * @param orderId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail/{orderId}")
    @ApiOperation(value = "根据订单ID获取订单详情")
    public HttpResult<OrderVO> detailById(
            @PathVariable @NotNull(message = "当前ID不能为空") String orderId) {
        return HttpResult.ok(orderService.detailById(orderId));
    }

    /**
     * 根据订单号获取详情
     *
     * @param orderNo 订单号
     * @return 详情数据
     */
    @GetMapping(value = "detail/{orderNo}")
    @ApiOperation(value = "根据订单号获取订单详情")
    public HttpResult<OrderVO> detailByNo(
            @PathVariable @NotNull(message = "当前订单编码不能为空") String orderNo) {
        return HttpResult.ok(orderService.detailByNo(orderNo));
    }

    /**
     * TODO 根据条件查询分页数据，查询参数自定义
     *
     * @param orderQueryDTO 订单查询参数
     * @return 查询分页数据
     */
    @GetMapping(value = "page")
    @ApiOperation(value = "根据订单分页列表")
    public HttpResult<Page<OrderVO>> page(OrderQueryDTO orderQueryDTO) {
        return HttpResult.ok(orderService.page(orderQueryDTO));
    }

    /**
     * 订单确认
     *
     * @param orderId 订单ID
     * @return 返回订单信息
     */
    @PostMapping(value = "confirm/{orderId}")
    public HttpResult<OrderVO> confirmOrder(@PathVariable String orderId) {
        return HttpResult.ok(orderService.confirmOrder(orderId));
    }
}