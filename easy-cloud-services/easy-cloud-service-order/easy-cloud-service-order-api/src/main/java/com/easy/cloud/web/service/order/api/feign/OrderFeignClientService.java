package com.easy.cloud.web.service.order.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.order.api.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.ORDER_SERVICE)
public interface OrderFeignClientService {

    /**
     * 支付成功回调
     *
     * @param orderNo 订单编号
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.order.biz.domain.vo.OrderVO>
     */
    @PostMapping("order/pay/success/handler/{orderNo}")
    HttpResult<Boolean> paySuccessHandler(@PathVariable(value = "orderNo") String orderNo);

    /**
     * 根据订单唯一编码获取订单详情
     *
     * @param no 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @GetMapping("order/detail/{no}")
    HttpResult<OrderVO> getOrderDetailByNo(@PathVariable(value = "no") String no);

    /**
     * 根据商品列表获取已支付的所有商品
     *
     * @param goodsNoList 商品列表
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("order/paid/off/verify")
    HttpResult<List<OrderVO>> verifyPaidOffGoods(@RequestBody List<String> goodsNoList);


    /**
     * 根据商品列表获取已支付的所有商品
     *
     * @param goodsNoList 商品列表
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("order/self/paid/off/verify")
    HttpResult<List<OrderVO>> selfVerifyPaidOffGoods(@RequestBody List<String> goodsNoList);

    /**
     * 获取时间范围内的订单列表
     *
     * @param orderDTO 查询参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("order/time/range/list")
    HttpResult<List<OrderVO>> orderListTimeRangeAndStatus(@RequestBody OrderDTO orderDTO);


    /**
     * 创建订单
     *
     * @param orderDTO 查询参数
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("order/create")
    HttpResult<OrderVO> createOrder(@RequestBody OrderDTO orderDTO);
}
