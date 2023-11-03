package com.easy.cloud.web.module.dict.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.dict.api.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.api.vo.DictItemVO;
import com.easy.cloud.web.module.dict.biz.converter.DictItemConverter;
import com.easy.cloud.web.module.dict.biz.domain.DictItemDO;
import com.easy.cloud.web.module.dict.biz.repository.DictItemRepository;
import com.easy.cloud.web.module.dict.biz.service.IDictItemService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DictItem 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-04 14:26:29
 */
@Slf4j
@Service
public class DictItemServiceImpl implements IDictItemService {

  @Autowired
  private DictItemRepository dictItemRepository;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void init() {
    // 未初始化过数据
    if (dictItemRepository.count() <= 0) {
      List<DictItemDO> dictItems = this
          .initJsonToList("json/system_dict_item.json", DictItemDO.class);
      dictItemRepository.saveAll(dictItems);
      log.info("init platform dictItem content success!");
    }
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public DictItemVO save(DictItemDTO dictItemDTO) {
    // 转换成DO对象
    DictItemDO dictItem = DictItemConverter.convertTo(dictItemDTO);
    // TODO 校验逻辑

    // 存储
    dictItemRepository.save(dictItem);
    // 转换对象
    return DictItemConverter.convertTo(dictItem);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public DictItemVO update(DictItemDTO dictItemDTO) {
    // 更新操作，ID不能为空
    if (Objects.isNull(dictItemDTO.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // 获取当前菜单信息
    DictItemDO dictItemDO = dictItemRepository.findById(dictItemDTO.getId())
        .orElseThrow(() -> new BusinessException("当前字典项信息不存在"));
    // 将修改的数据赋值给数据库数据
    BeanUtils.copyProperties(dictItemDTO, dictItemDO, true);
    // 存储
    dictItemRepository.save(dictItemDO);
    // 转换对象
    return DictItemConverter.convertTo(dictItemDO);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public Boolean removeById(String dictItemId) {
    // TODO 业务逻辑校验

    // 删除
    dictItemRepository.deleteById(dictItemId);
    return true;
  }

  @Override
  public List<DictItemVO> findByDictType(String dictType) {
    return dictItemRepository.findByDictType(dictType)
        .stream()
        .map(DictItemConverter::convertTo)
        .collect(Collectors.toList());
  }

  @Override
  public List<DictItemVO> findByDictTypes(List<String> dictTypes) {
    return dictItemRepository.findByDictTypeIn(dictTypes)
        .stream()
        .map(DictItemConverter::convertTo)
        .collect(Collectors.toList());
  }

  @Override
  public List<DictItemVO> list(String dictType) {
    // 获取列表数据
    List<DictItemDO> dictItems = dictItemRepository.findByDictType(dictType);
    return DictItemConverter.convertTo(dictItems);
  }
}