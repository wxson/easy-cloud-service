package com.easy.cloud.web.module.log.service.impl;

import com.easy.cloud.web.module.log.domain.OperationLogDO;
import com.easy.cloud.web.module.log.service.IOperationLogService;
import com.easy.cloud.web.module.log.converter.OperationLogConverter;
import com.easy.cloud.web.module.log.repository.OperationLogRepository;
import com.easy.cloud.web.service.upms.api.dto.OperationLogDTO;
import com.easy.cloud.web.service.upms.api.vo.OperationLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * OperationLog 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Slf4j
@Service
public class OperationLogServiceImpl implements IOperationLogService {

    @Autowired
    private OperationLogRepository operationLogRepository;

    @Override
    @Transactional
    public OperationLogVO save(OperationLogDTO operationLogDTO) {
        // 转换成DO对象
        OperationLogDO operationLog = OperationLogConverter.convertTo(operationLogDTO);
        // TODO 校验逻辑

        // 存储
        operationLogRepository.save(operationLog);
        // 转换对象
        return OperationLogConverter.convertTo(operationLog);
    }

    @Override
    @Transactional
    public OperationLogVO update(OperationLogDTO operationLogDTO) {
        // 转换成DO对象
        OperationLogDO operationLog = OperationLogConverter.convertTo(operationLogDTO);
        if (Objects.isNull(operationLog.getId())) {
            throw new RuntimeException("当前更新对象ID为空");
        }
        // TODO 业务逻辑校验

        // 更新
        operationLogRepository.save(operationLog);
        // 转换对象
        return OperationLogConverter.convertTo(operationLog);
    }

    @Override
    @Transactional
    public Boolean removeById(Long operationLogId) {
        // TODO 业务逻辑校验

        // 删除
        operationLogRepository.deleteById(operationLogId);
        return true;
    }

    @Override
    public OperationLogVO detailById(Long operationLogId) {
        // TODO 业务逻辑校验

        // 删除
        OperationLogDO operationLog = operationLogRepository.findById(operationLogId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return OperationLogConverter.convertTo(operationLog);
    }

    @Override
    public List<OperationLogVO> list() {
        // 获取列表数据
        List<OperationLogDO> operationLogs = operationLogRepository.findAll();
        return OperationLogConverter.convertTo(operationLogs);
    }

    @Override
    public Page<OperationLogVO> page(int page, int size) {
        // 构建分页数据
        Pageable pageable = PageRequest.of(page, size);
        return OperationLogConverter.convertTo(operationLogRepository.findAll(pageable));
    }
}