package com.easy.cloud.web.service.order.biz.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseQueryController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.order.biz.domain.db.OrderDO;
import com.easy.cloud.web.service.order.biz.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.biz.domain.query.OrderQuery;
import com.easy.cloud.web.service.order.biz.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Fast Java
 * @date 2021-11-12
 */
@Slf4j
@RestController
@RequestMapping("order")
@AllArgsConstructor
@Api(value = "订单管理", tags = "订单管理")
public class OrderController extends BaseQueryController<OrderQuery, OrderDO> {

    private final IOrderService orderService;

    @Override
    public IRepositoryService<OrderDO> getService() {
        return orderService;
    }

    /**
     * 创建订单
     *
     * @param orderDTO 订单数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
     */
    @PostMapping("create")
    @ApiOperation(value = "创建订单")
    public HttpResult<OrderVO> createOrder(@Validated @RequestBody OrderDTO orderDTO) {
        return HttpResult.ok(orderService.createOrder(orderDTO));
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


    /**
     * 根据订单编号获取订单详情
     *
     * @param no 订单数据
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
     */
    @GetMapping("detail/{no}")
    @ApiOperation(value = "根据订单编码获取订单详情")
    public HttpResult<OrderVO> getOrderByNo(@PathVariable @ApiParam("订单编号") String no) {
        if (StrUtil.isBlank(no)) {
            throw new BusinessException("订单唯一编码不能为空");
        }

        return HttpResult.ok(orderService.getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getNo, no)).convert());
    }

    /**
     * 根据商品唯一编码查询当前商品是否已支付
     *
     * @param goodsNoList 商品列表
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
     */
    @PostMapping("paid/off/verify")
    @ApiOperation(value = "根据商品编码校验是否存在已售订单")
    public HttpResult<List<OrderVO>> verifyPaidOffGoods(@RequestBody List<String> goodsNoList) {
        if (CollUtil.isEmpty(goodsNoList)) {
            return HttpResult.ok(CollUtil.newArrayList());
        }

        return HttpResult.ok(orderService.list(Wrappers.<OrderDO>lambdaQuery().in(OrderDO::getGoodsNo, goodsNoList)
                .eq(OrderDO::getOrderStatus, OrderStatusEnum.PAID_OFF.getCode()))
                .stream().map(OrderDO::convert).collect(Collectors.toList()));
    }

    /**
     * 根据商品唯一编码查询当前用户的商品是否已支付
     *
     * @param goodsNoList 商品列表
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
     */
    @PostMapping("self/paid/off/verify")
    @ApiOperation(value = "根据商品编码校验是否存在已售订单")
    public HttpResult<List<OrderVO>> selfVerifyPaidOffGoods(@RequestBody List<String> goodsNoList) {
        if (CollUtil.isEmpty(goodsNoList)) {
            return HttpResult.ok(CollUtil.newArrayList());
        }

        return HttpResult.ok(orderService.list(Wrappers.<OrderDO>lambdaQuery().in(OrderDO::getGoodsNo, goodsNoList)
                .eq(OrderDO::getOrderStatus, OrderStatusEnum.PAID_OFF.getCode())
                .eq(OrderDO::getUserId, SecurityUtils.getAuthenticationUser().getId()))
                .stream().map(OrderDO::convert).collect(Collectors.toList()));
    }


    /**
     * 根据时间范围获取订单列表
     *
     * @param orderQuery 商品列表
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
     */
    @PostMapping("time/range/list")
    @ApiOperation(value = "根据时间范围获取订单列表")
    public HttpResult<List<OrderVO>> orderListTimeRangeAndStatus(@RequestBody OrderQuery orderQuery) {
        return HttpResult.ok(orderService.list(Wrappers.<OrderDO>lambdaQuery()
                .ge(OrderDO::getCreateAt, orderQuery.getMinCreateAt())
                .lt(OrderDO::getCreateAt, orderQuery.getMaxCreateAt())
                .eq(StrUtil.isNotBlank(orderQuery.getUserId()), OrderDO::getUserId, orderQuery.getUserId())
                .eq(StrUtil.isNotBlank(orderQuery.getGoodsNo()), OrderDO::getGoodsNo, orderQuery.getGoodsNo())
                .eq(Objects.nonNull(orderQuery.getOrderStatus()), OrderDO::getOrderStatus, orderQuery.getOrderStatus()))
                .stream().map(OrderDO::convert).collect(Collectors.toList()));
    }
}