package com.easy.cloud.web.service.order.biz.service.factory.order;

import com.easy.cloud.web.service.order.api.dto.OrderCreateDTO;
import com.easy.cloud.web.service.order.api.enums.CurrencyTypeEnum;
import com.easy.cloud.web.service.order.api.enums.OrderTypeEnum;
import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商品订单生成器
 *
 * @author GR
 * @date 2024/2/5 9:41
 */
@Slf4j
@Service
public class GoodsOrderGenerateHandle implements IOrderGenerateHandle {

    @Override
    public OrderDO generateOrder(OrderCreateDTO orderCreateDTO) {
        // 1、查询当前商品信息

        // 2、根据商品信息创建订单
        return OrderDO.builder()
                .goodsNo(orderCreateDTO.getGoodsNo())
                .goodsName("测试商品")
                .goodsContent("测试商品内容")
                .goodsNum(orderCreateDTO.getGoodsNum())
                .orderType(OrderTypeEnum.GOODS)
                .currencyType(CurrencyTypeEnum.CNY)
                .amount(1)
                .discountsAmount(0)
                .salesPrice(0)
                .purchasePrice(0)
                .originalPrice(0)
                .build();
    }
}
