package com.easy.cloud.web.module.dict.converter;

import com.easy.cloud.web.module.dict.domain.db.DictItemDO;
import com.easy.cloud.web.module.dict.domain.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.domain.vo.DictItemVO;
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
   * @return com.easy.cloud.web.service.upms.biz.domain.db.DictItemDO
   */
  public static DictItemDO convertTo(DictItemDTO dictItem) {
    return DictItemDO.builder()
        .id(dictItem.getId())
        .name(dictItem.getName())
        .remark(dictItem.getRemark())
        .dictId(dictItem.getDictId())
        .describe(dictItem.getDescribe())
        .value(dictItem.getValue())
        .dictType(dictItem.getDictType())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param dictItem 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO
   */
  public static DictItemVO convertTo(DictItemDO dictItem) {
    return DictItemVO.builder()
        .id(dictItem.getId())
        .createBy(dictItem.getCreateBy())
        .createAt(dictItem.getCreateAt())
        .updateAt(dictItem.getUpdateAt())
        .name(dictItem.getName())
        .remark(dictItem.getRemark())
        .dictId(dictItem.getDictId())
        .describe(dictItem.getDescribe())
        .value(dictItem.getValue())
        .dictType(dictItem.getDictType())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param dictItems 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO
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
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO
   */
  public static Page<DictItemVO> convertTo(Page<DictItemDO> page) {
    return page.map(DictItemConverter::convertTo);
  }
}