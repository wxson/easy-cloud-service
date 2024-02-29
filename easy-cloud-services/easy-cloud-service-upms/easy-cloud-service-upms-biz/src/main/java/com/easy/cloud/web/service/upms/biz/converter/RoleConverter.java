package com.easy.cloud.web.service.upms.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
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
    RoleDO roleDO = RoleDO.builder().build();
    BeanUtils.copyProperties(role, roleDO, true);
    return roleDO;
  }

  /**
   * DO转为VO
   *
   * @param role 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.RoleVO
   */
  public static RoleVO convertTo(RoleDO role) {
    RoleVO roleVO = RoleVO.builder().build();
    BeanUtils.copyProperties(role, roleVO, true);
    return roleVO;
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