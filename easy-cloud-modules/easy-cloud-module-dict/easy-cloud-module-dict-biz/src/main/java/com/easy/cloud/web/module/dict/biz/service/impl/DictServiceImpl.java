package com.easy.cloud.web.module.dict.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.dict.api.dto.DictDTO;
import com.easy.cloud.web.module.dict.api.vo.DictItemVO;
import com.easy.cloud.web.module.dict.api.vo.DictVO;
import com.easy.cloud.web.module.dict.biz.converter.DictConverter;
import com.easy.cloud.web.module.dict.biz.domain.DictDO;
import com.easy.cloud.web.module.dict.biz.repository.DictRepository;
import com.easy.cloud.web.module.dict.biz.service.IDictItemService;
import com.easy.cloud.web.module.dict.biz.service.IDictService;
import java.util.List;
import java.util.Map;
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
  private IDictItemService dictItemService;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void init() {
    // 未初始化过数据
    if (dictRepository.count() <= 0) {
      List<DictDO> dicts = this.initJsonToList("json/system_dict.json", DictDO.class);
      dictRepository.saveAll(dicts);
      log.info("init platform dict content success!");
    }
  }


  @Override
  @Transactional(rollbackOn = Exception.class)
  public DictVO save(DictDTO dictDTO) {
    // 校验是否已存在相同类型的字典
    DictDO existDict = dictRepository.findByType(dictDTO.getType());
    if (Objects.nonNull(existDict)) {
      throw new BusinessException("当前字典类型已存在");
    }
    // 转换成DO对象
    DictDO dict = DictConverter.convertTo(dictDTO);
    // TODO 校验逻辑
    // 存储
    dictRepository.save(dict);
    // 转换对象
    return DictConverter.convertTo(dict);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public DictVO update(DictDTO dictDTO) {
    // 更新操作，ID不能为空
    if (Objects.isNull(dictDTO.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // 获取当前菜单信息
    DictDO dictDO = dictRepository.findById(dictDTO.getId())
        .orElseThrow(() -> new BusinessException("当前菜单信息不存在"));
    // 将修改的数据赋值给数据库数据
    BeanUtils.copyProperties(dictDTO, dictDO, true);
    // 更新
    dictRepository.save(dictDO);
    // 转换对象
    return DictConverter.convertTo(dictDO);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public Boolean removeById(String dictId) {
    // TODO 业务逻辑校验

    // 删除
    dictRepository.deleteById(dictId);
    return true;
  }

  @Override
  public DictVO detailById(String dictId) {
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
    List<DictItemVO> dictItems = dictItemService.findByDictType(dict.getType());
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
//    Page<DictDO> doPage = dictRepository.findAll(pageable);
    Page<DictVO> pageVO = DictConverter.convertTo(dictRepository.findAll(pageable));
    List<String> dictTypes = pageVO.getContent().stream().map(DictVO::getType)
        .collect(Collectors.toList());
    Map<String, List<DictItemVO>> dictItemMap = dictItemService.findByDictTypes(dictTypes).stream()
        .collect(Collectors.groupingBy(DictItemVO::getDictType));
    for (DictVO dictVO : pageVO.getContent()) {
      if (dictItemMap.containsKey(dictVO.getType())) {
        dictVO.setItems(dictItemMap.get(dictVO.getType()));
      }
    }
    return pageVO;
  }
}