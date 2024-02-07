package com.easy.cloud.web.service.upms.biz.social.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.enums.GenderEnum;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 微信
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
public class WxAppSocialServiceImpl implements ISocialService {

    /**
     * APP_ID
     */
    private final String APP_ID = "wxe51ae9c0adc39103";
    /**
     * SECRET
     */
    private final String APP_SECRET = "XXXXXXXXXXXXXXXXXXXXXXX";

    @Override
    public SocialTypeEnum getType() {
        return SocialTypeEnum.WX_APP;
    }

    @Override
    public UserDO loadSocialUser(UserLoginDTO userLogin) {
        // 构建accessTokenAPI
        String wxAccessTokenApi = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
        // 构建TOKEN_API
        String accessTokenApi = StrUtil.format(wxAccessTokenApi, APP_ID, APP_SECRET, userLogin.getCode());
        // 获取响应体
        String accessTokenResponseBody = HttpUtil.get(accessTokenApi);
        log.info("---- 微信登录：请求AccessToken 响应报文：{}", accessTokenResponseBody);
        // 解析返回数据
        JSONObject accessTokenResponseJsonObject = JSONUtil.parseObj(accessTokenResponseBody);
        // 获取access_token
        Object openId = accessTokenResponseJsonObject.get("openid");
        Object accessToken = accessTokenResponseJsonObject.get("access_token");
        if (Objects.isNull(openId) || Objects.isNull(accessToken)) {
            throw new BusinessException("当前验证码已过期或已使用");
        }

        // 微信用户API
        String wxUserApi = "https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}";
        // 构建USER_API
        String userApi = StrUtil.format(wxUserApi, accessToken, openId);
        // 用户响应体
        String userResponseBody = HttpUtil.get(userApi);
        log.info("wx app userResponseBody：{}", userResponseBody);
        // 解析返回数据
        JSONObject userResponseJsonObject = JSONUtil.parseObj(userResponseBody);
        return UserDO.builder()
                .nickName(userResponseJsonObject.getStr("nickname"))
                .unionId(userResponseJsonObject.getStr("unionid"))
                .avatar(userResponseJsonObject.getStr("headimgurl"))
                .province(userResponseJsonObject.getStr("province"))
                .city(userResponseJsonObject.getStr("city"))
                .gender(GenderEnum.getInstance(userResponseJsonObject.getInt("sex")))
                .build();
    }
}
