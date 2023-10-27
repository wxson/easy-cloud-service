package com.easy.cloud.web.service.upms.biz.social.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.upms.api.enums.GenderEnum;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
public class WxSocialServiceImpl implements ISocialService {

  @Override
  public SocialTypeEnum getType() {
    return SocialTypeEnum.WX;
  }

  @Override
  public UserDO loadSocialUser(String code) {
    String appId = "xxx";
    String secret = "xxxxxxxxxxxxxxxxxxxxxxx";
    String wxAccessTokenApi = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
    // 构建TOKEN_API
    String accessTokenApi = StrUtil.format(wxAccessTokenApi, appId, secret, code);
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
