package com.easy.cloud.web.service.upms.biz.social.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
public class WxSocialServiceImpl implements ISocialService {

  /**
   * APP_ID
   */
  private final String APP_ID = "XXXXX";
  /**
   * SECRET
   */
  private final String APP_SECRET = "XXXXXXXXXXXXXXXXXXXXXXX";

  @Override
  public SocialTypeEnum getType() {
    return SocialTypeEnum.WX;
  }

  @Override
  public UserDO loadSocialUser(UserLoginDTO userLogin) {
    // 构建用户信息
    UserDO userDO = UserDO.builder().build();
    // 获取电话信息信息
    if (StrUtil.isNotBlank(userLogin.getPhoneCode())) {
      JSONObject telJson = this.getUserTelInfo(userLogin);
      if (Objects.nonNull(telJson)) {
        userDO.setTel(telJson.getStr("phoneNumber"));
        userDO.setNickName(userDO.getTel());
        userDO.setUserName(userDO.getTel());
      }
    }

    // 获取用户信息
    if (StrUtil.isNotBlank(userLogin.getCode())) {
      JSONObject userInfoJson = this.getUserInfo(userLogin);
      if (Objects.nonNull(userInfoJson)) {
        userDO.setUnionId(userInfoJson.getStr("unionid"));
      }
    }

    // 电话和unionId都为空，用户授权登录失败
    if (StrUtil.isBlank(userDO.getTel()) && StrUtil.isBlank(userDO.getUnionId())) {
      throw new BusinessException("用户授权登录失败");
    }

    return userDO;
  }

  /**
   * 获取登录用户电话信息
   *
   * @param userLogin 登录信息
   * @return
   */
  private JSONObject getUserTelInfo(UserLoginDTO userLogin) {
    // 电话code为空
    if (StrUtil.isBlank(userLogin.getPhoneCode())) {
      return null;
    }
    // 获取用户电话信息
    String tokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APP_ID, APP_SECRET);
    // 解析返回数据
    JSONObject telTokenData = JSONUtil.parseObj(HttpUtil.get(tokenUrl));
    // 获取access_token数据
    String access_token = telTokenData.getStr("access_token");
    if (StrUtil.isBlank(access_token)) {
      return null;
    }
    //通过token和code来获取用户手机号
    String url = String.format("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s", access_token);
    //封装请求体
    Map<String, Object> paramMap = new HashMap<>(1);
    paramMap.put("code", userLogin.getPhoneCode());
    JSONObject telResponseBody = JSONUtil.parseObj(HttpUtil.post(url, JSONUtil.toJsonStr(paramMap)));
    return telResponseBody.getJSONObject("phone_info");
  }

  /**
   * 获取登录用户信息
   *
   * @param userLogin 登录信息
   * @return
   */
  private JSONObject getUserInfo(UserLoginDTO userLogin) {
    if (StrUtil.isBlank(userLogin.getCode())) {
      return null;
    }

    String wxAccessTokenApi = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";
    // 构建TOKEN_API
    String accessTokenApi = StrUtil.format(wxAccessTokenApi, APP_ID, APP_SECRET, userLogin.getCode());
    // 获取响应体
    String accessTokenResponseBody = HttpUtil.get(accessTokenApi);
    log.info("---- 微信登录：请求AccessToken 响应报文：{}", accessTokenResponseBody);
    // 解析返回数据
    JSONObject userResponseBody = JSONUtil.parseObj(accessTokenResponseBody);
    // 解析返回数据
    return JSONUtil.parseObj(userResponseBody);
  }
}
