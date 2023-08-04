package com.easy.cloud.web.module.dict.service.impl;

import com.easy.cloud.web.module.dict.converter.DictItemConverter;
import com.easy.cloud.web.module.dict.domain.db.DictItemDO;
import com.easy.cloud.web.module.dict.domain.dto.DictItemDTO;
import com.easy.cloud.web.module.dict.domain.vo.DictItemVO;
import com.easy.cloud.web.module.dict.repository.DictItemRepository;
import com.easy.cloud.web.module.dict.service.IDictItemService;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Transactional
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
    @Transactional
    public DictItemVO update(DictItemDTO dictItemDTO) {
        // 转换成DO对象
        DictItemDO dictItem = DictItemConverter.convertTo(dictItemDTO);
        if (Objects.isNull(dictItem.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        dictItemRepository.save(dictItem);
        // 转换对象
        return DictItemConverter.convertTo(dictItem);
    }

    @Override
    @Transactional
    public Boolean removeById(Long dictItemId) {
        // TODO 业务逻辑校验

        // 删除
        dictItemRepository.deleteById(dictItemId);
        return true;
    }

    @Override
    public DictItemVO detailById(Long dictItemId) {
        // TODO 业务逻辑校验

        // 删除
        DictItemDO dictItem = dictItemRepository.findById(dictItemId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return DictItemConverter.convertTo(dictItem);
    }

    @Override
    public List<DictItemVO> list() {
        // 获取列表数据
        List<DictItemDO> dictItems = dictItemRepository.findAll();
        return DictItemConverter.convertTo(dictItems);
    }

    @Override
    public Page<DictItemVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(page, size);
        return DictItemConverter.convertTo(dictItemRepository.findAll(pageable));
    }
}