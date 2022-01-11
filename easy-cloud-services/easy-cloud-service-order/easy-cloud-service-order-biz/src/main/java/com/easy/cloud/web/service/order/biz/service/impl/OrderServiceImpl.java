package com.easy.cloud.web.service.order.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.cms.api.domain.vo.GoodsVO;
import com.easy.cloud.web.service.cms.api.feign.CmsFeignClientService;
import com.easy.cloud.web.service.order.biz.domain.db.OrderDO;
import com.easy.cloud.web.service.order.biz.domain.dto.OrderDTO;
import com.easy.cloud.web.service.order.biz.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.enums.*;
import com.easy.cloud.web.service.order.biz.mapper.OrderMapper;
import com.easy.cloud.web.service.order.biz.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * Order 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-11-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements IOrderService {

    private final CmsFeignClientService cmsFeignClientService;

    @Override
    public OrderVO createOrder(OrderDTO orderDTO) {
        // TODO 校验库存是否充足

        // 初始化订单信息
        OrderDO orderDO = this.initOrderInfo(orderDTO);
        // 保存订单数据
        this.save(orderDO);
        // 返回订单数据
        return OrderVO.builder().no(orderDO.getNo()).build();
    }

    @Override
    public Boolean paySuccessHandler(String orderNo) {
        if (StrUtil.isBlank(orderNo)) {
            throw new BusinessException("当前订单编号不能为空");
        }

        // 获取修改订单状态
        this.update(Wrappers.<OrderDO>lambdaUpdate()
                .eq(OrderDO::getNo, orderNo)
                .set(OrderDO::getOrderStatus, OrderStatusEnum.PAID_OFF.getCode())
                .set(OrderDO::getPayStatus, PayStatusEnum.PAY_YES.getCode())
                .set(OrderDO::getCreatePayAt, DateUtil.now()));
        return true;
    }

    /**
     * 初始化订单信息
     *
     * @param orderDTO 订单信息
     * @return com.easy.cloud.web.service.order.biz.domain.db.OrderDO
     */
    private OrderDO initOrderInfo(OrderDTO orderDTO) {
        // 获取商品信息
        GoodsVO goodsVO = cmsFeignClientService.getGoodsDetailByNo(orderDTO.getGoodsNo()).getData();
        if (Objects.isNull(goodsVO)) {
            throw new BusinessException("获取商品信息详情失败，请稍后再试");
        }

        // 订单金额
        BigDecimal amount = NumberUtil.mul(goodsVO.getSalesPrice(), orderDTO.getGoodsNum());
        // 优惠价格
        BigDecimal discountsPrice = NumberUtil.sub(goodsVO.getOriginalPrice(), goodsVO.getSalesPrice());
        // 优惠金额
        BigDecimal discountsAmount = NumberUtil.mul(discountsPrice.abs(), orderDTO.getGoodsNum());
        // 创建订单
        return OrderDO.build()
                .setUserId(SecurityUtils.getAuthenticationUser().getId())
                .setNo(this.createOrderNo(goodsVO))
                .setGoodsNo(goodsVO.getNo())
                .setGoodsName(goodsVO.getName())
                .setGoodsContent(goodsVO.getContent())
                .setGoodsNum(orderDTO.getGoodsNum())
                .setAmount(amount)
                .setCurrencyType(goodsVO.getCurrencyType())
                .setDiscountsAmount(discountsAmount)
                .setSalesPrice(goodsVO.getSalesPrice())
                .setPurchasePrice(goodsVO.getPurchasePrice())
                .setOriginalPrice(goodsVO.getOriginalPrice())
                .setAftermarketStatus(AftermarketStatusEnum.NOT_APPLY.getCode())
                .setCommentStatus(CommentStatusEnum.UN_COMMENT.getCode())
                .setFinishStatus(FinishStatusEnum.UNFINISHED.getCode())
                .setLogisticsStatus(LogisticsStatusEnum.SHIP_NO.getCode())
                .setOrderStatus(OrderStatusEnum.NEW.getCode())
                .setCreateOrderAt(DateUtil.now())
                .setPayStatus(PayStatusEnum.PAY_NO.getCode());
    }

    /**
     * 创建订单唯一编码
     *
     * @param goodsVO 商品信息
     * @return java.lang.String
     */
    private String createOrderNo(GoodsVO goodsVO) {
        // 商品格式：NO. + 时间戳 + goodsType + currencyType + 5位随机数
        String goodsType = Optional.ofNullable(goodsVO.getGoodsType()).orElse(0) > 9 ? "" + goodsVO.getGoodsType() : "0" + goodsVO.getGoodsType();
        String currencyType = Optional.ofNullable(goodsVO.getCurrencyType()).orElse(0) > 9 ? "" + goodsVO.getCurrencyType() : "0" + goodsVO.getCurrencyType();
        return "NO" + DateUtil.now().replaceAll("[-|:| ]", "").trim() + goodsType + currencyType + RandomUtil.randomNumbers(5);
    }
}