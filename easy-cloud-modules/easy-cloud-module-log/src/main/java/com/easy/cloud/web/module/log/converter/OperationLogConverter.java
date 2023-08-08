package com.easy.cloud.web.module.log.converter;

import com.easy.cloud.web.module.log.domain.OperationLogDO;
import com.easy.cloud.web.service.upms.api.dto.OperationLogDTO;
import com.easy.cloud.web.service.upms.api.vo.OperationLogVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * OperationLog转换器
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
public class OperationLogConverter {

  /**
   * DTO转为DO
   *
   * @param operationLog 转换数据
   * @return com.easy.cloud.web.module.log.domain.OperationLogDO
   */
  public static OperationLogDO convertTo(OperationLogDTO operationLog) {
    return OperationLogDO.builder()
        .name(operationLog.getName())
        .action(operationLog.getAction())
        .methodName(operationLog.getMethod())
        .type(operationLog.getType())
        .params(operationLog.getParams())
        .elapsedTime(operationLog.getElapsedTime())
        .build();
  }

  /**
   * DO转为VO
   *
   * @param operationLog 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  public static OperationLogVO convertTo(OperationLogDO operationLog) {
    return OperationLogVO.builder()
        .id(operationLog.getId())
        .createAt(operationLog.getCreateAt())
        .name(operationLog.getName())
        .action(operationLog.getAction())
        .methodName(operationLog.getMethodName())
        .className(operationLog.getClassName())
        .type(operationLog.getType())
        .params(operationLog.getParams())
        .elapsedTime(operationLog.getElapsedTime())
        .build();
  }

  /**
   * 列表DO转为VO
   *
   * @param operationLogs 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  public static List<OperationLogVO> convertTo(List<OperationLogDO> operationLogs) {
    return operationLogs.stream()
        .map(OperationLogConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  public static Page<OperationLogVO> convertTo(Page<OperationLogDO> page) {
    return page.map(OperationLogConverter::convertTo);
  }
}