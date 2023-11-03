package com.easy.cloud.web.module.dict.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.dict.api.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.api.vo.DictItemVO;
import com.easy.cloud.web.module.dict.biz.domain.DictItemDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * DictItem转换器
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
public class DictItemConverter {

  /**
   * DTO转为DO
   *
   * @param dictItem 转换数据
   * @return com.easy.cloud.web.service.upms.api.domain.db.DictItemDO
   */
  public static DictItemDO convertTo(DictItemDTO dictItem) {
    DictItemDO dictItemDO = DictItemDO.builder().build();
    BeanUtils.copyProperties(dictItem, dictItemDO, true);
    return dictItemDO;
  }

  /**
   * DO转为VO
   *
   * @param dictItem 转换数据
   * @return com.easy.cloud.web.service.upms.api.domain.vo.DictItemVO
   */
  public static DictItemVO convertTo(DictItemDO dictItem) {
    DictItemVO dictItemVO = DictItemVO.builder().build();
    BeanUtils.copyProperties(dictItem, dictItemVO, true);
    dictItemVO
        .setCreateAt(DateUtil.format(dictItem.getCreateAt(), DateTimeConstants.DEFAULT_FORMAT));
    return dictItemVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param dictItems 转换数据
   * @return com.easy.cloud.web.service.upms.api.domain.vo.DictItemVO
   */
  public static List<DictItemVO> convertTo(List<DictItemDO> dictItems) {
    return dictItems.stream()
        .map(DictItemConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.domain.vo.DictItemVO
   */
  public static Page<DictItemVO> convertTo(Page<DictItemDO> page) {
    return page.map(DictItemConverter::convertTo);
  }
}