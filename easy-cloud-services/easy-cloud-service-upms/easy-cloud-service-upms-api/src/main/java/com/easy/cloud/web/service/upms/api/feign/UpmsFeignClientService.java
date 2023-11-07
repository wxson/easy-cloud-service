package com.easy.cloud.web.service.upms.api.feign;

import cn.hutool.json.JSONObject;
import com.easy.cloud.web.component.core.constants.ServiceNameConstants;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.upms.api.dto.UserBindDTO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GR
 * @date 2021-11-8 11:33
 */
@FeignClient(contextId = "upmsFeignClientService", value = ServiceNameConstants.UPMS_SERVICE)
public interface UpmsFeignClientService {

  /**
   * 根据用户ID获取用户信息
   *
   * @param userId 用户ID
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
   */
  @GetMapping("user/info/{userId}")
  HttpResult<UserVO> loadUserByUserId(@PathVariable(value = "userId") String userId);

  /**
   * 根据用户account获取用户信息
   *
   * @param account 用户账号
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
   */
  @GetMapping("user/account/{account}")
  HttpResult<UserVO> loadUserByAccount(@PathVariable(value = "account") String account);

  /**
   * 获取授权信息
   *
   * @param userName 用户名称
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
   */
  @GetMapping("user/detail/{userName}")
  HttpResult<UserVO> loadUserByUsername(@PathVariable("userName") String userName);

  /**
   * 获取授权信息
   *
   * @param type       登录类型
   * @param jsonObject 认证编码
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
   */
  @PostMapping("user/detail/{type}")
  HttpResult<UserVO> loadSocialUser(@PathVariable(value = "type") String type,
      @RequestBody JSONObject jsonObject);

  /**
   * 根据用户ID获取用户列表
   *
   * @param userIdList 用户ID集合
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.api.domain.User>
   */
  @PostMapping("user/batch/list")
  HttpResult<List<UserVO>> batchList(@RequestBody List<String> userIdList);

  /**
   * 用户实名
   *
   * @param userBindDTO 用户信息
   * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
   */
  @PostMapping("user/certification")
  HttpResult<Boolean> certification(@RequestBody UserBindDTO userBindDTO);

  /**
   * 绑定用户手机号
   *
   * @param userBindDTO 用户信息
   * @return com.easy.cloud.web.component.core.response.HttpResult<java.lang.Boolean>
   */
  @PostMapping("user/bind/tel")
  HttpResult<Boolean> bindUserTel(@RequestBody UserBindDTO userBindDTO);
}
