package com.easy.cloud.web.service.member.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * MemberBalance 请求数据
 *
 * @author Fast Java
 * @date 2023-10-11 11:11:41
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberBalanceDTO {


  /**
   * 资产来源
   */
  private Integer origin;
  /**
   * 订单编号
   */
  private String orderNo;

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
   *
   */
  private String userId;
}