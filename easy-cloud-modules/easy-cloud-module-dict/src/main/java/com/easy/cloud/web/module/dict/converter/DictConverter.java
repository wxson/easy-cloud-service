package com.easy.cloud.web.module.dict.converter;

import com.easy.cloud.web.module.dict.domain.db.DictDO;
import com.easy.cloud.web.module.dict.domain.dto.DictDTO;
import com.easy.cloud.web.module.dict.domain.vo.DictVO;
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
    return DictDO.builder()
        .id(dict.getId())
        .name(dict.getName())
        .remark(dict.getRemark())
        .describe(dict.getDescribe())
        .type(dict.getType())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param dict 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictVO
   */
  public static DictVO convertTo(DictDO dict) {
    return DictVO.builder()
        .id(dict.getId())
        .createBy(dict.getCreateBy())
        .createAt(dict.getCreateAt())
        .updateAt(dict.getUpdateAt())
        .name(dict.getName())
        .remark(dict.getRemark())
        .describe(dict.getDescribe())
        .type(dict.getType())
        .build();
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