package com.easy.cloud.web.service.pay.biz.service.pay;

import com.easy.cloud.web.service.pay.api.dto.BillDTO;
import com.easy.cloud.web.service.pay.api.dto.PayDTO;
import com.easy.cloud.web.service.pay.api.enums.PayTypeEnum;
import com.easy.cloud.web.service.pay.api.vo.BillVO;
import com.easy.cloud.web.service.pay.api.vo.PayVO;
import com.easy.cloud.web.service.pay.biz.service.IPayHandleService;
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
public class AliPayServiceImpl implements IPayHandleService {

  @Override
  public PayTypeEnum type() {
    return PayTypeEnum.ALI_PAY;
  }

  @Override
  public PayVO pay(PayDTO payDTO) {
    return null;
  }

  @Override
  public BillVO bill(BillDTO billDTO) {
    return null;
  }
}
