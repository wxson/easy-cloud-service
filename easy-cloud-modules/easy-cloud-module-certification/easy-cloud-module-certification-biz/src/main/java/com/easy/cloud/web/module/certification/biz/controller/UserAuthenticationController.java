package com.easy.cloud.web.module.certification.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.vo.UserAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.service.IUserAuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserAuthentication API
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Slf4j
@RestController
@RequestMapping(value = "user/authentication")
@Api(value = "UserAuthentication", tags = "用户认证管理")
public class UserAuthenticationController {

    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    /**
     * 根据用户ID获取详情
     *
     * @param userId ID
     * @return 详情数据
     */
    @GetMapping(value = "detail")
    @ApiOperation(value = "获取用户认证信息")
    public HttpResult<UserAuthenticationVO> detailById(@RequestParam String userId) {
        return HttpResult.ok(userAuthenticationService.detailById(userId));
    }
}