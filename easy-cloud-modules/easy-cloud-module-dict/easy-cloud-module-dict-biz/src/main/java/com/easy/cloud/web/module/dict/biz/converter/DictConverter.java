package com.easy.cloud.web.module.dict.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.dict.api.dto.DictDTO;
import com.easy.cloud.web.module.dict.api.vo.DictVO;
import com.easy.cloud.web.module.dict.biz.domain.DictDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Dict转换器
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
public class DictConverter {

  /**
   * DTO转为DO
   *
   * @param dict 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.DictDO
   */
  public static DictDO convertTo(DictDTO dict) {
    DictDO dictDO = DictDO.builder().build();
    BeanUtils.copyProperties(dict, dictDO, true);
    return dictDO;
  }

  /**
   * DO转为VO
   *
   * @param dict 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictVO
   */
  public static DictVO convertTo(DictDO dict) {
    DictVO dictVO = DictVO.builder().build();
    BeanUtils.copyProperties(dict, dictVO, true);
    dictVO.setCreateAt(DateUtil.format(dict.getCreateAt(), DateTimeConstants.DEFAULT_FORMAT));
    return dictVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param dicts 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictVO
   */
  public static List<DictVO> convertTo(List<DictDO> dicts) {
    return dicts.stream()
        .map(DictConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictVO
   */
  public static Page<DictVO> convertTo(Page<DictDO> page) {
    return page.map(DictConverter::convertTo);
  }
}