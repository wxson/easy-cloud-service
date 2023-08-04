package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * User请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements IConverter {

  /**
   * 文档ID
   */
  private Long id;
  /**
   * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身 一个企业、一个单位或一所学校只能有一个租户
   */
  private String tenantId;
  /**
   * 微信union Id
   */
  private String unionId;
  /**
   * 苹果ID
   */
  private String appleId;
  /**
   * 国家
   */
  private String country;
  /**
   * 省
   */
  private String province;
  /**
   * 市
   */
  private String city;
  /**
   * 区域
   */
  private String region;
  /**
   * 昵称
   */
  private String nickName;
  /**
   * 账号
   */
  private String account;
  /**
   * 密码
   */
  private String password;
  /**
   * 性别： 0 未知的性别 1 男 2 女 9 未说明的性别
   */
  private Integer sex;
  /**
   * 用户名
   */
  private String userName;
  /**
   * 身份证
   */
  private String identity;
  /**
   * 电话
   */
  private String tel;

  /**
   * 状态 0 启用 1 禁用
   */
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  private DeletedEnum deleted;
  /**
   * 头像
   */
  private String avatar;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 创建用户
   */
  private String createBy;
  /**
   * 创建时间
   */
  private String createAt;
  /**
   * 更新用户
   */
  private String updateBy;
  /**
   * 更新时间
   */
  private String updateAt;
}