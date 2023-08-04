package com.easy.cloud.web.module.dict.service.impl;

import com.easy.cloud.web.module.dict.converter.DictConverter;
import com.easy.cloud.web.module.dict.domain.db.DictDO;
import com.easy.cloud.web.module.dict.domain.dto.DictDTO;
import com.easy.cloud.web.module.dict.domain.vo.DictItemVO;
import com.easy.cloud.web.module.dict.domain.vo.DictVO;
import com.easy.cloud.web.module.dict.repository.DictItemRepository;
import com.easy.cloud.web.module.dict.repository.DictRepository;
import com.easy.cloud.web.module.dict.service.IDictService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Dict 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-04 14:22:33
 */
@Slf4j
@Service
public class DictServiceImpl implements IDictService {

  @Autowired
  private DictRepository dictRepository;

  @Autowired
  private DictItemRepository dictItemRepository;

  @Override
  public void init() {
    if (dictRepository.count() <= 0) {
      // 未初始化过数据
      if (dictRepository.count() <= 0) {
        log.info("init platform dict content success!");
      }
    }
  }


  @Override
  @Transactional
  public DictVO save(DictDTO dictDTO) {
    // 转换成DO对象
    DictDO dict = DictConverter.convertTo(dictDTO);
    // TODO 校验逻辑

    // 存储
    dictRepository.save(dict);
    // 转换对象
    return DictConverter.convertTo(dict);
  }

  @Override
  @Transactional
  public DictVO update(DictDTO dictDTO) {
    // 转换成DO对象
    DictDO dict = DictConverter.convertTo(dictDTO);
    if (Objects.isNull(dict.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    dictRepository.save(dict);
    // 转换对象
    return DictConverter.convertTo(dict);
  }

  @Override
  @Transactional
  public Boolean removeById(Long dictId) {
    // TODO 业务逻辑校验

    // 删除
    dictRepository.deleteById(dictId);
    return true;
  }

  @Override
  public DictVO detailById(Long dictId) {
    // TODO 业务逻辑校验

    // 删除
    DictDO dict = dictRepository.findById(dictId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    return this.findDictItems(dict);
  }

  @Override
  public DictVO detailByType(String dictType) {
    // TODO 业务逻辑校验

    // 删除
    DictDO dict = Optional.ofNullable(dictRepository.findByType(dictType))
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    return this.findDictItems(dict);
  }

  /**
   * 根据字典获取字典项
   *
   * @param dict 字典数据
   * @return
   */
  private DictVO findDictItems(DictDO dict) {
    // 转换
    DictVO dictVO = dict.convertTo(DictVO.class);
    // 获取字典子项
    List<DictItemVO> dictItems = dictItemRepository.findByDictType(dict.getType()).stream()
        .map(dictItemDO -> dictItemDO.convertTo(DictItemVO.class))
        .collect(Collectors.toList());
    dictVO.setItems(dictItems);
    return dictVO;
  }

  @Override
  public List<DictVO> list() {
    // 获取列表数据
    List<DictDO> dicts = dictRepository.findAll();
    return DictConverter.convertTo(dicts);
  }

  @Override
  public Page<DictVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return DictConverter.convertTo(dictRepository.findAll(pageable));
  }
}