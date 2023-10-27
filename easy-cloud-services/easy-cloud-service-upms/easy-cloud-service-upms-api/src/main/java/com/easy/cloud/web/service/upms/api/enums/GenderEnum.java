package com.easy.cloud.web.service.upms.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 人的性别代码
 *
 * @author GR
 * @date 2020-9-22 16:44
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
  /**
   * 未知的性别
   */
  UN_KNOWN(0, "未知的性别"),
  /**
   * 男性
   */
  MAN(1, "男"),
  /**
   * 女性
   */
  WOMAN(2, "女"),
  /**
   * 未说明的性别
   */
  UN_SPECIFIED(9, "未说明的性别");
  private final int code;
  private final String desc;

  public static GenderEnum getInstance(int gender) {
    for (GenderEnum genderEnum : GenderEnum.values()) {
      if (genderEnum.getCode() == gender) {
        return genderEnum;
      }
    }
    return null;
  }
}
