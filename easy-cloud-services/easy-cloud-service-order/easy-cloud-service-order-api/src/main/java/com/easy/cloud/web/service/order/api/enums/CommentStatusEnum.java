package com.easy.cloud.web.service.order.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论状态枚举
 *
 * @author GR
 * @date 2021-8-31 22:26
 */
@Getter
@AllArgsConstructor
public enum CommentStatusEnum implements IBaseEnum {
  /**
   * 未评论（UN_COMMENT），已评论（COMMENTED）。
   */
  UN_COMMENT(1, "未评论"),
  COMMENTED(2, "已评论"),
  ;
  /**
   * 编码
   */
  private final int code;
  /**
   * 描述
   */
  private final String desc;

  /**
   * 根据code获取对象
   *
   * @param code 编码
   * @return java.util.Optional<com.easy.cloud.web.service.mj.biz.enums.HuCardRuleEnum>
   */
  public static Optional<CommentStatusEnum> getInstanceByCode(int code) {
    for (CommentStatusEnum orderStatusEnum : CommentStatusEnum.values()) {
      if (code == orderStatusEnum.code) {
        return Optional.of(orderStatusEnum);
      }
    }
    return Optional.empty();
  }
}
