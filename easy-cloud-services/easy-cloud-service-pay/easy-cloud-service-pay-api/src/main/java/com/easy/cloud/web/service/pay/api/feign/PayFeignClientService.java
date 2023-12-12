package com.easy.cloud.web.service.pay.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.pay.api.dto.PayDTO;
import com.easy.cloud.web.service.pay.api.vo.PayVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.PAY_SERVICE)
public interface PayFeignClientService {

    /**
     * 支付
     *
     * @param payDTO 支付内容
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
     */
    @PostMapping("order")
    HttpResult<PayVO> pay(@RequestBody PayDTO payDTO);
}
