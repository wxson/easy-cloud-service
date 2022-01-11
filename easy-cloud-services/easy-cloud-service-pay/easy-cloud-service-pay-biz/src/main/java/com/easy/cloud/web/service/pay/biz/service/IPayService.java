package com.easy.cloud.web.service.pay.biz.service;

import com.easy.cloud.web.service.pay.biz.domain.dto.BillDTO;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.BillVO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;

/**
 * @author GR
 * @date 2021-11-12 14:41
 */
public interface IPayService {

    /**
     * 支付回调
     *
     * @param payDTO 回调信息
     * @return java.lang.Object
     */
    Object payCallbackHandler(PayDTO payDTO);

    /**
     * 支付
     *
     * @param payDTO 支付信息
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.PayVO
     */
    PayVO payOrder(PayDTO payDTO);

    /**
     * 支付
     *
     * @param payDTO 支付信息
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.PayVO
     */
    PayVO payGoods(PayDTO payDTO);

    /**
     * 发放奖励
     *
     * @param orderNo 订单编号
     * @param goodsNo 商品编号
     */
    void grantPrize(String orderNo, String goodsNo);


    /**
     * 账单信息
     *
     * @param billDTO 查询账单条件
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.BillVO
     */
    BillVO bill(BillDTO billDTO);
}
