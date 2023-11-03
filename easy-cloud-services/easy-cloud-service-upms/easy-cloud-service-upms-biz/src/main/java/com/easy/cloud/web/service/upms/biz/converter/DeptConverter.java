package com.easy.cloud.web.service.upms.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.DeptDTO;
import com.easy.cloud.web.service.upms.api.vo.DeptVO;
import com.easy.cloud.web.service.upms.biz.domain.DeptDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * dept转换器
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
public class DeptConverter {

  /**
   * DTO转为DO
   *
   * @param dept 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.deptDO
   */
  public static DeptDO convertTo(DeptDTO dept) {
    DeptDO deptDO = DeptDO.builder().build();
    BeanUtils.copyProperties(dept, deptDO, true);
    return deptDO;
  }

  /**
   * DO转为VO
   *
   * @param dept 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.deptVO
   */
  public static DeptVO convertTo(DeptDO dept) {
    DeptVO deptVO = DeptVO.builder().build();
    BeanUtils.copyProperties(dept, deptVO, true);
    deptVO.setCreateAt(DateUtil.format(dept.getCreateAt(), DateTimeConstants.DEFAULT_FORMAT));
    deptVO.setUpdateAt(DateUtil.format(dept.getUpdateAt(), DateTimeConstants.DEFAULT_FORMAT));
    return deptVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param depts 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.deptVO
   */
  public static List<DeptVO> convertTo(List<DeptDO> depts) {
    return depts.stream()
        .map(DeptConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.deptVO
   */
  public static Page<DeptVO> convertTo(Page<DeptDO> page) {
    return page.map(DeptConverter::convertTo);
  }
}