package com.easy.cloud.web.service.member.api.feign.fallback;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import org.springframework.stereotype.Component;

/**
 * 会员服务降级
 *
 * @author GR
 * @date 2024/2/18 10:49
 */
@Component
public class MemberFallback implements MemberFeignClientService {

    @Override
    public HttpResult<MemberVO> detailByUserId(String userId) {
        return HttpResult.ok();
    }
}
