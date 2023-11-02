package com.easy.cloud.web.service.upms.biz.converter;

import com.easy.cloud.web.service.upms.api.dto.RoleDTO;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import com.easy.cloud.web.service.upms.biz.domain.RoleDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Role转换器
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
public class RoleConverter {

  /**
   * DTO转为DO
   *
   * @param role 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.RoleDO
   */
  public static RoleDO convertTo(RoleDTO role) {
    return RoleDO.builder()
        .id(role.getId())
        .deleted(role.getDeleted())
        .tenantId(role.getTenantId())
        .name(role.getName())
        .remark(role.getRemark())
        .status(role.getStatus())
        .code(role.getCode())
        .sort(role.getSort())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param role 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.RoleVO
   */
  public static RoleVO convertTo(RoleDO role) {
    return RoleVO.builder()
        .id(role.getId())
        .createBy(role.getCreateBy())
        .createAt(role.getCreateAt())
        .updateAt(role.getUpdateAt())
        .deleted(role.getDeleted())
        .tenantId(role.getTenantId())
        .name(role.getName())
        .remark(role.getRemark())
        .status(role.getStatus())
        .code(role.getCode())
        .sort(role.getSort())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param roles 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.RoleVO
   */
  public static List<RoleVO> convertTo(List<RoleDO> roles) {
    return roles.stream()
        .map(RoleConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.RoleVO
   */
  public static Page<RoleVO> convertTo(Page<RoleDO> page) {
    return page.map(RoleConverter::convertTo);
  }
}