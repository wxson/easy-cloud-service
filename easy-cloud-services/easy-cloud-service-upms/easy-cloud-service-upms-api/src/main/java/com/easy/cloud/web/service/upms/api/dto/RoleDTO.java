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
 * Role请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements IConverter {

  /**
   * 文档ID，必须保证角色ID的全局唯一性
   */
  private String id;
  /**
   * 租户ID
   */
  private String tenantId;
  /**
   * 角色编码
   */
  private String code;
  /**
   * 角色名称
   */
  private String name;
  /**
   * 描述
   */
  private String describe;

  /**
   * 状态 0 启用 1 禁用
   */
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  private DeletedEnum deleted;
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