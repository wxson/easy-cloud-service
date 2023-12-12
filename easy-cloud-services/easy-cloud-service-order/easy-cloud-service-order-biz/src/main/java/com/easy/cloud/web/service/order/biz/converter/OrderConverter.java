package com.easy.cloud.web.service.order.biz.converter;

import com.easy.cloud.web.service.order.api.dto.OrderDTO;
import com.easy.cloud.web.service.order.api.vo.OrderVO;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Order转换器
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
public class OrderConverter {

  /**
   * DTO转为DO
   *
   * @param order 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.db.OrderDO
   */
  public static OrderDO convertTo(OrderDTO order) {
    return OrderDO.builder()
        .id(order.getId())
        .currencyType(order.getCurrencyType())
        .no(order.getNo())
        .originalPrice(order.getOriginalPrice())
        .salesPrice(order.getSalesPrice())
        .orderStatus(order.getOrderStatus())
        .purchasePrice(order.getPurchasePrice())
        .completeCommentAt(order.getCompleteCommentAt())
        .goodsContent(order.getGoodsContent())
        .createCommentAt(order.getCreateCommentAt())
        .goodsName(order.getGoodsName())
        .goodsNum(order.getGoodsNum())
        .aftermarketStatus(order.getAftermarketStatus())
        .completeAftermarketAt(order.getCompleteAftermarketAt())
        .goodsNo(order.getGoodsNo())
        .createLogisticsAt(order.getCreateLogisticsAt())
        .amount(order.getAmount())
        .createAftermarketAt(order.getCreateAftermarketAt())
        .createPayAt(order.getCreatePayAt())
        .completePayAt(order.getCompletePayAt())
        .userId(order.getUserId())
        .commentStatus(order.getCommentStatus())
        .createOrderAt(order.getCreateOrderAt())
        .finishStatus(order.getFinishStatus())
        .completeOrderAt(order.getCompleteOrderAt())
        .discountsAmount(order.getDiscountsAmount())
        .logisticsStatus(order.getLogisticsStatus())
        .payStatus(order.getPayStatus())
        .finishAt(order.getFinishAt())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param order 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  public static OrderVO convertTo(OrderDO order) {
    return OrderVO.builder()
        .id(order.getId())
        .currencyType(order.getCurrencyType())
        .no(order.getNo())
        .originalPrice(order.getOriginalPrice())
        .salesPrice(order.getSalesPrice())
        .orderStatus(order.getOrderStatus())
        .purchasePrice(order.getPurchasePrice())
        .completeCommentAt(order.getCompleteCommentAt())
        .goodsContent(order.getGoodsContent())
        .createCommentAt(order.getCreateCommentAt())
        .goodsName(order.getGoodsName())
        .goodsNum(order.getGoodsNum())
        .aftermarketStatus(order.getAftermarketStatus())
        .completeAftermarketAt(order.getCompleteAftermarketAt())
        .goodsNo(order.getGoodsNo())
        .createLogisticsAt(order.getCreateLogisticsAt())
        .amount(order.getAmount())
        .createAftermarketAt(order.getCreateAftermarketAt())
        .createPayAt(order.getCreatePayAt())
        .completePayAt(order.getCompletePayAt())
        .userId(order.getUserId())
        .commentStatus(order.getCommentStatus())
        .createOrderAt(order.getCreateOrderAt())
        .finishStatus(order.getFinishStatus())
        .completeOrderAt(order.getCompleteOrderAt())
        .discountsAmount(order.getDiscountsAmount())
        .logisticsStatus(order.getLogisticsStatus())
        .payStatus(order.getPayStatus())
        .finishAt(order.getFinishAt())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param orders 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  public static List<OrderVO> convertTo(List<OrderDO> orders) {
    return orders.stream()
        .map(OrderConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.order.biz.domain.vo.OrderVO
   */
  public static Page<OrderVO> convertTo(Page<OrderDO> page) {
    return page.map(OrderConverter::convertTo);
  }
}