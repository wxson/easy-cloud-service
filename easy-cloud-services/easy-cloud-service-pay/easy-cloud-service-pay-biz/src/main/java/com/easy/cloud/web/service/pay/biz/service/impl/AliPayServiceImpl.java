package com.easy.cloud.web.service.pay.biz.service.impl;

import com.easy.cloud.web.service.pay.biz.domain.dto.BillDTO;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.BillVO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;
import com.easy.cloud.web.service.pay.biz.service.IPayProxyService;
import com.easy.cloud.web.service.pay.biz.service.IPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付宝支付
 *
 * @author GR
 * @date 2021-11-12 14:41
 */
@Slf4j
@Service
@AllArgsConstructor
public class AliPayServiceImpl implements IPayProxyService {
    @Override
    public PayVO pay(PayDTO payDTO) {
        return null;
    }

    @Override
    public BillVO bill(BillDTO billDTO) {
        return null;
    }
}
