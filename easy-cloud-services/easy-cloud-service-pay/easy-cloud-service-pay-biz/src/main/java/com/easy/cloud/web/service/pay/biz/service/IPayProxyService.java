package com.easy.cloud.web.service.pay.biz.service;

import com.easy.cloud.web.service.pay.biz.domain.dto.BillDTO;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.BillVO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;

/**
 * @author GR
 * @date 2021-11-12 14:41
 */
public interface IPayProxyService {

    /**
     * 支付
     *
     * @param payDTO 支付信息
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.PayVO
     */
    PayVO pay(PayDTO payDTO);


    /**
     * 账单信息
     *
     * @param billDTO 查询账单条件
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.BillVO
     */
    BillVO bill(BillDTO billDTO);
}
