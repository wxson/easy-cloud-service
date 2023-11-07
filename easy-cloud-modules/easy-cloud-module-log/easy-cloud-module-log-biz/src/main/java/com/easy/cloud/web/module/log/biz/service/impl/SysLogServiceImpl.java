package com.easy.cloud.web.module.log.biz.service.impl;

import com.easy.cloud.web.module.log.api.dto.SysLogDTO;
import com.easy.cloud.web.module.log.api.vo.SysLogVO;
import com.easy.cloud.web.module.log.biz.converter.SysLogConverter;
import com.easy.cloud.web.module.log.biz.domain.SysLogDO;
import com.easy.cloud.web.module.log.biz.repository.SysLogRepository;
import com.easy.cloud.web.module.log.biz.service.ISysLogService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * OperationLog 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Slf4j
@Service
public class SysLogServiceImpl implements ISysLogService {

  @Autowired
  private SysLogRepository sysLogRepository;

  @Override
  @Transactional
  public SysLogVO save(SysLogDTO sysLogDTO) {
    // 转换成DO对象
    SysLogDO operationLog = SysLogConverter.convertTo(sysLogDTO);
    // TODO 校验逻辑

    // 存储
    sysLogRepository.save(operationLog);
    // 转换对象
    return SysLogConverter.convertTo(operationLog);
  }

  @Override
  public SysLogVO detailById(String sysLogId) {
    // TODO 业务逻辑校验

    // 删除
    SysLogDO operationLog = sysLogRepository.findById(sysLogId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return SysLogConverter.convertTo(operationLog);
  }

  @Override
  public List<SysLogVO> list() {
    // 获取列表数据
    List<SysLogDO> operationLogs = sysLogRepository.findAll();
    return SysLogConverter.convertTo(operationLogs);
  }

  @Override
  public Page<SysLogVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return SysLogConverter.convertTo(sysLogRepository.findAll(pageable));
  }
}