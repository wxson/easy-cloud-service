package com.easy.cloud.web.service.pay.biz.domain.dto;

import com.easy.cloud.web.service.pay.biz.enums.PayTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账单内容
 *
 * @author GR
 * @date 2021-11-12 14:43
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class BillDTO {

    /**
     * 支付方式
     */
    private PayTypeEnum payType;

}
