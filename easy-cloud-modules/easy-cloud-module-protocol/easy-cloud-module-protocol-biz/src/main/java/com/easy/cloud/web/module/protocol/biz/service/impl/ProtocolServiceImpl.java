package com.easy.cloud.web.module.protocol.biz.service.impl;

import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.protocol.api.dto.ProtocolDTO;
import com.easy.cloud.web.module.protocol.api.vo.ProtocolVO;
import com.easy.cloud.web.module.protocol.biz.converter.ProtocolConverter;
import com.easy.cloud.web.module.protocol.biz.domain.ProtocolDO;
import com.easy.cloud.web.module.protocol.biz.repository.ProtocolRepository;
import com.easy.cloud.web.module.protocol.biz.service.IProtocolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Protocol 业务逻辑
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
@Slf4j
@Service
public class ProtocolServiceImpl implements IProtocolService {

    @Autowired
    private ProtocolRepository protocolRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProtocolVO save(ProtocolDTO protocolDTO) {
        // 转换成DO对象
        ProtocolDO protocol = ProtocolConverter.convertTo(protocolDTO);
        // TODO 校验逻辑

        // 存储
        protocolRepository.save(protocol);
        // 转换对象
        return ProtocolConverter.convertTo(protocol);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProtocolVO update(ProtocolDTO protocolDTO) {
        // ID 不能为空
        if (Objects.isNull(protocolDTO.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }

        ProtocolDO protocol = protocolRepository.findById(protocolDTO.getId()).orElseThrow(() -> new BusinessException("当前信息不存在"));
        // TODO 业务逻辑校验

        // 赋值属性，过滤空值制
        BeanUtils.copyProperties(protocolDTO, protocol, true);
        // 更新
        protocolRepository.save(protocol);
        // 转换对象
        return ProtocolConverter.convertTo(protocol);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean removeById(String protocolId) {
        // TODO 业务逻辑校验

        // 删除
        protocolRepository.deleteById(protocolId);
        return true;
    }

    @Override
    public ProtocolVO detailById(String protocolId) {
        // TODO 业务逻辑校验

        // 删除
        ProtocolDO protocol = protocolRepository.findById(protocolId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return ProtocolConverter.convertTo(protocol);
    }

    @Override
    public ProtocolVO appDetailByUniqueId(String uniqueId) {
        ProtocolDO protocol = protocolRepository.findByUniqueId(uniqueId);
        return Objects.isNull(protocol) ? null : ProtocolConverter.convertTo(protocol);
    }

    @Override
    public List<ProtocolVO> list() {
        // 获取列表数据
        List<ProtocolDO> protocols = protocolRepository.findAll();
        return ProtocolConverter.convertTo(protocols);
    }

    @Override
    public Page<ProtocolVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(Math.max(GlobalCommonConstants.ZERO, page - 1), size);
        return ProtocolConverter.convertTo(protocolRepository.findAll(pageable));
    }
}