package com.easy.cloud.web.service.upms.api.dto;

import java.util.List;
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
public class UserBindDTO {

  /**
   * 文档ID
   */
  private String id;

  /**
   * 用户姓名
   */
  private String userName;

  /**
   * 用户身份证号
   */
  private String identity;

  /**
   * 用户电话
   */
  private String tel;

  /**
   * 密码
   */
  private String password;

  /**
   * 新密码
   */
  private String newPassword;

  /**
   * 角色ID
   */
  private List<String> roleIds;

}