package com.easy.cloud.web.service.member.api.feign;

import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.member.api.feign.fallback.MemberFallback;
import com.easy.cloud.web.service.member.api.vo.MemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(value = ServiceNameConstants.MEMBER_SERVICE, fallback = MemberFallback.class)
public interface MemberFeignClientService {

    /**
     * 根据用户ID获取会员信息
     *
     * @param userId 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.member.api.vo.MemberVO>
     */
    @GetMapping("member/detail/{userId}")
    HttpResult<MemberVO> detailByUserId(@PathVariable(value = "userId") String userId);
}
