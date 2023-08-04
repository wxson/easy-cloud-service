package com.easy.cloud.web.module.dict.service;

import com.easy.cloud.web.module.dict.domain.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.domain.vo.DictItemVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * DictItem interface
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
public interface IDictItemService {

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
  Boolean removeById(Long dictItemId);

  /**
   * 根据ID获取详情
   *
   * @param dictItemId 对象ID
   * @return java.lang.Boolean
   */
  DictItemVO detailById(Long dictItemId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO> 返回列表数据
   */
  List<DictItemVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictItemVO> 返回列表数据
   */
  Page<DictItemVO> page(int page, int size);
}