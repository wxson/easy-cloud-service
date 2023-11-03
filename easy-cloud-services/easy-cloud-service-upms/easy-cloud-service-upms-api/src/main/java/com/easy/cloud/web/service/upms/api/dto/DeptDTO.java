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
 * Department请求数据
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeptDTO implements IConverter {

  /**
   * 文档ID
   */
  private String id;
  /**
   * 部门名称
   */
  private String name;
  /**
   * 上级部门
   */
  private String parentId;
  /**
   * 负责人
   */
  private String leader;
  /**
   * 联系电话
   */
  private String tel;
  /**
   * 邮箱
   */
  private String email;
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
}