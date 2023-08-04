package com.easy.cloud.web.service.upms.api.enums;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式类型枚举
 *
 * @author GR
 */
@Getter
@AllArgsConstructor
public enum SocialTypeEnum {
  /**
   * 角色枚举类型
   */
  // 使用ShareSDK授权登录，微信、QQ使用同一套登录逻辑
  WX(1, "wx", "微信登录"),
  QQ(2, "qq", "QQ登录"),
  APPLE(3, "apple", "苹果登录"),
  ;

  private final int code;
  private final String prefix;
  private final String describe;

  /**
   * 根据类型获取枚举类
   *
   * @param type 登录类型
   * @return java.util.Optional<com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum>
   */
  public static Optional<SocialTypeEnum> getSocialByType(String type) {
    for (SocialTypeEnum socialTypeEnum : SocialTypeEnum.values()) {
      if (socialTypeEnum.prefix.equals(type)) {
        return Optional.of(socialTypeEnum);
      }
    }
    return Optional.empty();
  }
}
