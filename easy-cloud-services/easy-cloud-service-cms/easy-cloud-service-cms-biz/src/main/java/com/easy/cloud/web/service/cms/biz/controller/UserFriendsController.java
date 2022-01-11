package com.easy.cloud.web.service.cms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.cms.biz.service.IUserFriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 签到
 *
 * @author GR
 * @date 2021-11-26 12:32
 */
@Slf4j
@RestController
@RequestMapping("user/friends")
@AllArgsConstructor
@Api(value = "好友管理", tags = "好友管理")
public class UserFriendsController {

    private final IUserFriendsService userFriendsService;


    /**
     * 绑定好友ID，即会员账号
     *
     * @param friendId 好友ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.boolean>
     */
    @PostMapping("bind/friends/{friendId}")
    @ApiOperation(value = "绑定好友ID")
    public HttpResult<Boolean> bindFriendId(@PathVariable @ApiParam("好友ID") String friendId) {
        return HttpResult.ok(userFriendsService.bindFriendId(friendId));
    }
}
