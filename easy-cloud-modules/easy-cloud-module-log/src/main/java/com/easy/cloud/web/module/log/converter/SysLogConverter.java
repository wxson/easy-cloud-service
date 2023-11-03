package com.easy.cloud.web.module.log.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.log.domain.SysLogDO;
import com.easy.cloud.web.service.upms.api.dto.SysLogDTO;
import com.easy.cloud.web.service.upms.api.vo.SysLogVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * OperationLog转换器
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
public class SysLogConverter {

  /**
   * DTO转为DO
   *
   * @param operationLog 转换数据
   * @return com.easy.cloud.web.module.log.domain.OperationLogDO
   */
  public static SysLogDO convertTo(SysLogDTO operationLog) {
    return SysLogDO.builder()
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
   * @param sysLog 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  public static SysLogVO convertTo(SysLogDO sysLog) {
    SysLogVO sysLogVO = SysLogVO.builder().build();
    BeanUtils.copyProperties(sysLog, sysLogVO, true);
    sysLogVO.setCreateAt(DateUtil.format(sysLog.getCreateAt(), DateTimeConstants.DEFAULT_FORMAT));
    return sysLogVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param operationLogs 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  public static List<SysLogVO> convertTo(List<SysLogDO> operationLogs) {
    return operationLogs.stream()
        .map(SysLogConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  public static Page<SysLogVO> convertTo(Page<SysLogDO> page) {
    return page.map(SysLogConverter::convertTo);
  }
}