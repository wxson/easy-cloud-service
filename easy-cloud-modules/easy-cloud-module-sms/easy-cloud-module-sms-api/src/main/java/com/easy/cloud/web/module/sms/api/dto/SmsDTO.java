package com.easy.cloud.web.module.sms.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Sms请求数据
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SmsDTO {

  /**
   * 文档ID
   */
  private String id;
  /**
   *
   */
  private String code;
  /**
   *
   */
  private String tel;
  /**
   *
   */
  private String content;
  /**
   * 创建用户
   */
  private String creatorAt;
  /**
   * 创建时间
   */
  private String createAt;
  /**
   * 更新时间
   */
  private String updateAt;
}