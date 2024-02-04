package com.easy.cloud.web.component.pay.service.impl;

import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
import com.easy.cloud.web.component.pay.service.IPayClientService;
import com.easy.cloud.web.component.pay.service.IPayFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * 支付工厂
 *
 * @author GR
 * @date 2024/2/4 15:39
 */
@Slf4j
@Service
public class PayFactoryImpl implements IPayFactory, ApplicationContextAware {

    /**
     * 支付类型方式
     */
    private final Map<PayTypeEnum, IPayClientService> payClientServices = new EnumMap<>(PayTypeEnum.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(IPayClientService.class).values()
                .forEach(payHandleService -> payClientServices.put(payHandleService.type(), payHandleService));
    }

    @Override
    public IPayClientService getPayClient(PayTypeEnum payType) {
        return payClientServices.get(payType);
    }
}
