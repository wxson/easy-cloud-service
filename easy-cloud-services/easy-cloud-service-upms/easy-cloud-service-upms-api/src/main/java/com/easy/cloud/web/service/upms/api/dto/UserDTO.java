package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import com.easy.cloud.web.service.upms.api.enums.GenderEnum;
import java.util.Set;
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
  private String id;
  /**
   * 租户ID,超管、管理、租户三个角色的租户ID都是自己本身 一个企业、一个单位或一所学校只能有一个租户
   * <p>只有超级管理员才能传递租户信息</p>
   */
  protected String tenantId;
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
   * 真实名字
   */
  private String realName;
  /**
   * 密码
   */
  private String password;
  /**
   * 性别： 0 未知的性别 1 男 2 女 9 未说明的性别
   */
  private GenderEnum gender;
  /**
   * 用户名、用户账号
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
   * 角色ID
   */
  private Set<String> roleIds;
}