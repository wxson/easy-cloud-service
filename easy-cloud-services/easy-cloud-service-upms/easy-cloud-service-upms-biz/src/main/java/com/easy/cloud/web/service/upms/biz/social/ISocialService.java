package com.easy.cloud.web.service.upms.biz.social;

import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;

/**
 * 第三方业务逻辑
 *
 * @author GR
 * @date 2021-11-11 10:20
 */
public interface ISocialService {

  /**
   * 获取实现类型
   *
   * @return
   */
  SocialTypeEnum getType();

  /**
   * 根据认证编码获取用户信息
   *
   * @param code 认证编码
   * @return UserDO
   */
  UserDO loadSocialUser(String code);
}
