package com.easy.cloud.web.component.pay.service.client.alipay;

import com.easy.cloud.web.component.pay.service.IPayClientService;
import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
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
public class AliPayClientService implements IPayClientService {

    @Override
    public PayTypeEnum type() {
        return PayTypeEnum.ALI_PAY;
    }

    @Override
    public PayResponseBody pay(PayRequestBody payRequestBody) {
        return null;
    }
}
