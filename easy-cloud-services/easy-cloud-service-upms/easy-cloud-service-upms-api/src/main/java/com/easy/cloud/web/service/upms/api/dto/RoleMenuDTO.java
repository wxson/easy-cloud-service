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
public class RoleMenuDTO {

  /**
   * 角色ID
   */
  private String roleId;

  /**
   * 菜单ID
   */
  private List<String> menuIds;

}