package com.easy.cloud.web.service.cms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.service.cms.biz.domain.db.GoodsDO;
import com.easy.cloud.web.service.cms.biz.domain.vo.GoodsVO;
import com.easy.cloud.web.service.cms.biz.domain.vo.ShopVO;
import com.easy.cloud.web.service.cms.biz.enums.ActionEnum;
import com.easy.cloud.web.service.cms.biz.enums.GoodsTypeEnum;
import com.easy.cloud.web.service.cms.biz.service.IGoodsService;
import com.easy.cloud.web.service.cms.biz.service.IShopService;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.enums.OrderStatusEnum;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商城业务逻辑
 *
 * @author GR
 * @date 2021-11-30 11:38
 */
@Slf4j
@Service
@AllArgsConstructor
public class ShopServiceImpl implements IShopService {

    private final IGoodsService goodsService;

    private final OrderFeignClientService orderFeignClientService;

    @Override
    public List<ShopVO> list() {
        // 创建商城商品分类
        ArrayList<Integer> goodsTypeList = CollUtil.newArrayList(
                GoodsTypeEnum.DIAMOND.getCode(),
                GoodsTypeEnum.GOLD_COIN.getCode(),
                GoodsTypeEnum.COUPON.getCode(),
                GoodsTypeEnum.TABLECLOTH.getCode(),
                GoodsTypeEnum.CARD_BACK.getCode(),
                GoodsTypeEnum.PROP_EXCHANGE.getCode()
        );
        // 获取所有商品
        List<GoodsDO> goodsDOList = goodsService.list(Wrappers.<GoodsDO>lambdaQuery()
                // 不参与活动商品
                .eq(GoodsDO::getAction, ActionEnum.DEFAULT.getCode())
                .in(GoodsDO::getGoodsType, goodsTypeList));

        // 获取商品编号
        List<String> goodsNoList = goodsDOList.stream().map(GoodsDO::getNo).collect(Collectors.toList());
        List<OrderVO> orderVOList = orderFeignClientService.selfVerifyPaidOffGoods(goodsNoList).getData();
        // 订单列表
        if (CollUtil.isEmpty(orderVOList)) {
            orderVOList = new ArrayList<>();
        }

        // 获取订购列表
        List<String> orderGoodsNoList = orderVOList.stream().map(OrderVO::getGoodsNo).collect(Collectors.toList());

        // 分组
        return goodsDOList.stream()
                .collect(Collectors.groupingBy(GoodsDO::getGoodsType))
                .entrySet()
                .stream()
                .map(entry -> {
                    ShopVO shopVO = ShopVO.build().setGoodsType(entry.getKey());
                    List<GoodsVO> goodsVOList = entry.getValue().stream().map(goodsDO -> {
                        GoodsVO goodsVO = goodsDO.convert();
                        if (orderGoodsNoList.contains(goodsDO.getNo())) {
                            goodsVO.setPaidOff(OrderStatusEnum.PAID_OFF.getCode());
                        }
                        GoodsTypeEnum.getInstanceByCode(goodsDO.getGoodsType()).ifPresent(goodsTypeEnum -> shopVO.setGoodsTypeName(goodsTypeEnum.getDesc()));
                        return goodsVO;
                    }).collect(Collectors.toList());
                    shopVO.setGoodsList(goodsVOList);
                    return shopVO;
                }).collect(Collectors.toList());
    }
}
