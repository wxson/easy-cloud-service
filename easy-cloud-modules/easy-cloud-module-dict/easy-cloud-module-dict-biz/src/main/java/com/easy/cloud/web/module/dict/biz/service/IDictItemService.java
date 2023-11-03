package com.easy.cloud.web.module.dict.biz.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.module.dict.api.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.api.vo.DictItemVO;
import java.util.List;

/**
 * DictItem interface
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
public interface IDictItemService extends IInitService {

  /**
   * 新增数据
   *
   * @param dictItemDTO 保存参数
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO
   */
  DictItemVO save(DictItemDTO dictItemDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param dictItemDTO 保存参数
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO
   */
  DictItemVO update(DictItemDTO dictItemDTO);

  /**
   * 根据ID删除数据
   *
   * @param dictItemId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(String dictItemId);

  /**
   * 根据条件获取列表数据
   *
   * @param dictType 字典类型
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO> 返回列表数据
   */
  List<DictItemVO> findByDictType(String dictType);

  /**
   * 根据条件获取列表数据
   *
   * @param dictTypes 字典类型
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO> 返回列表数据
   */
  List<DictItemVO> findByDictTypes(List<String> dictTypes);

  /**
   * 根据条件获取列表数据
   *
   * @param dictType 字典类型
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO> 返回列表数据
   */
  List<DictItemVO> list(String dictType);
}