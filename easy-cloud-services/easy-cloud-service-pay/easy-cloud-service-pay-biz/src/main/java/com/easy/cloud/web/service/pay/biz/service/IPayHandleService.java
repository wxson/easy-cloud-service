package com.easy.cloud.web.service.pay.biz.service;

import com.easy.cloud.web.service.pay.api.dto.BillDTO;
import com.easy.cloud.web.service.pay.api.dto.PayDTO;
import com.easy.cloud.web.service.pay.api.enums.PayTypeEnum;
import com.easy.cloud.web.service.pay.api.vo.BillVO;
import com.easy.cloud.web.service.pay.api.vo.PayVO;

/**
 * @author GR
 * @date 2021-11-12 14:41
 */
public interface IPayHandleService {

  /**
   * 支付类型
   *
   * @return
   */
  PayTypeEnum type();

  /**
   * 支付
   *
   * @param payDTO 支付信息
   * @return com.easy.cloud.web.service.pay.api.vo.PayVO
   */
  PayVO pay(PayDTO payDTO);


  /**
   * 账单信息
   *
   * @param billDTO 查询账单条件
   * @return com.easy.cloud.web.service.pay.api.vo.BillVO
   */
  BillVO bill(BillDTO billDTO);
}
