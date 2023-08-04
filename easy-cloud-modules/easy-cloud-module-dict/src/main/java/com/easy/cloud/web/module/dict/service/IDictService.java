package com.easy.cloud.web.module.dict.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.module.dict.domain.dto.DictDTO;
import com.easy.cloud.web.module.dict.domain.vo.DictVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Dict interface
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
public interface IDictService extends IInitService {

  /**
   * 新增数据
   *
   * @param dictDTO 保存参数
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictVO
   */
  DictVO save(DictDTO dictDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param dictDTO 保存参数
   * @return com.easy.cloud.web.service.upms.biz.domain.vo.DictVO
   */
  DictVO update(DictDTO dictDTO);

  /**
   * 根据ID删除数据
   *
   * @param dictId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(Long dictId);

  /**
   * 根据ID获取详情
   *
   * @param dictId 对象ID
   * @return java.lang.Boolean
   */
  DictVO detailById(Long dictId);

  /**
   * 根据字典类型获取详情
   *
   * @param dictType 字典类型
   * @return java.lang.Boolean
   */
  DictVO detailByType(String dictType);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictVO> 返回列表数据
   */
  List<DictVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.biz.domain.vo.DictVO> 返回列表数据
   */
  Page<DictVO> page(int page, int size);
}