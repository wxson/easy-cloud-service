package com.easy.cloud.web.service.member.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Member展示数据
 *
 * @author Fast Java
 * @date 2023-12-12 10:48:07
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

  /**
   * 文档ID
   */
  private String id;
  /**
   * vip等级
   */
  private Integer vipLevel;
  /**
   * 金币
   */
  private Integer amount;
  /**
   * 钻石
   */
  private Integer diamond;
  /**
   * 点券
   */
  private Integer coupon;
  /**
   * 等级
   */
  private Integer level;
  /**
   * IP形象：可是文字，也可是图片
   */
  private String ip;
  /**
   * 签名
   */
  private String profile;
  /**
   * 经验
   */
  private Integer experience;
  /**
   * 总资产
   */
  private Integer totalRecharge;
  /**
   * 用户ID
   */
  private String userId;


  /**
   * 账号
   */
  private String account;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 性别
   */
  private Integer sex;

}