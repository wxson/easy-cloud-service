package com.easy.cloud.web.component.pay.service.client.virtualpay;

import com.easy.cloud.web.component.pay.service.IPayClientService;
import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 钻石支付
 *
 * @author GR
 * @date 2021-11-12 14:41
 */
@Slf4j
@Service
@AllArgsConstructor
public class VirtualPayClientService implements IPayClientService {

    @Override
    public PayTypeEnum type() {
        return PayTypeEnum.VIRTUAL_PAY;
    }

    @Override
    public PayResponseBody pay(PayRequestBody payRequestBody) {
        return null;
    }
}
