package com.easy.cloud.web.service.pay.biz.enums;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.enums.IBaseEnum;
import com.easy.cloud.web.component.core.util.SpringContextHolder;
import com.easy.cloud.web.service.pay.biz.service.IPayProxyService;
import com.easy.cloud.web.service.pay.biz.service.impl.AliPayServiceImpl;
import com.easy.cloud.web.service.pay.biz.service.impl.VirtualPayServiceImpl;
import com.easy.cloud.web.service.pay.biz.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 支付方式枚举
 *
 * @author GR
 * @date 2021-11-12 14:47
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum implements IBaseEnum {
    /**
     * 支付方式
     */
    WX_PAY(1, "微信支付", WxPayServiceImpl.class),
    ALI_PAY(2, "阿里支付", AliPayServiceImpl.class),
    VIRTUAL_PAY(9, "虚拟货币支付", VirtualPayServiceImpl.class),
    ;
    private final int code;
    private final String desc;
    private final Class<?> implClass;

    /**
     * 获取实现类
     *
     * @return java.util.Optional<com.easy.cloud.web.service.pay.biz.service.IPayProxyService>
     */
    public Optional<IPayProxyService> getPayService() {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getCode() == code) {
                String simpleName = payTypeEnum.getImplClass().getSimpleName();
                return Optional.of(SpringContextHolder.getBean(StrUtil.lowerFirst(simpleName), IPayProxyService.class));
            }
        }
        return Optional.empty();
    }
}
