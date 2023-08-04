package com.easy.cloud.web.service.upms.biz.converter;

import com.easy.cloud.web.service.upms.biz.domain.DepartmentDO;
import com.easy.cloud.web.service.upms.api.dto.DepartmentDTO;
import com.easy.cloud.web.service.upms.api.vo.DepartmentVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Department转换器
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
public class DepartmentConverter {

  /**
   * DTO转为DO
   *
   * @param department 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.DepartmentDO
   */
  public static DepartmentDO convertTo(DepartmentDTO department) {
    return DepartmentDO.builder()
        .id(department.getId())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param department 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  public static DepartmentVO convertTo(DepartmentDO department) {
    return DepartmentVO.builder()
        .id(department.getId())
        .createBy(department.getCreateBy())
        .createAt(department.getCreateAt())
        .updateAt(department.getUpdateAt())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param departments 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  public static List<DepartmentVO> convertTo(List<DepartmentDO> departments) {
    return departments.stream()
        .map(DepartmentConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.DepartmentVO
   */
  public static Page<DepartmentVO> convertTo(Page<DepartmentDO> page) {
    return page.map(DepartmentConverter::convertTo);
  }
}