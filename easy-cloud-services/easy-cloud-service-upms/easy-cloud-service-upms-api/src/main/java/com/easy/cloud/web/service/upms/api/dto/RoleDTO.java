package com.easy.cloud.web.service.upms.api.dto;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.service.IConverter;
import java.util.Set;
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
  private String remark;
  /**
   * 排序
   */
  private Integer sort;

  /**
   * 状态 0 启用 1 禁用
   */
  private StatusEnum status;
  /**
   * 是否删除 0 未删除 1 已删除
   */
  private DeletedEnum deleted;

  /**
   * 权限列表
   */
  private Set<String> menuIds;
}