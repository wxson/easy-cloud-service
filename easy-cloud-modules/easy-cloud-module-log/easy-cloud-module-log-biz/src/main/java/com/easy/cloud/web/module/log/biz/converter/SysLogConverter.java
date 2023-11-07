package com.easy.cloud.web.module.log.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.module.log.api.dto.SysLogDTO;
import com.easy.cloud.web.module.log.api.vo.SysLogVO;
import com.easy.cloud.web.module.log.biz.domain.SysLogDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * sysLog转换器
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
public class SysLogConverter {

  /**
   * DTO转为DO
   *
   * @param sysLog 转换数据
   * @return com.easy.cloud.web.module.log.domain.sysLogDO
   */
  public static SysLogDO convertTo(SysLogDTO sysLog) {
    SysLogDO sysLogDO = SysLogDO.builder().build();
    BeanUtils.copyProperties(sysLog, sysLogDO, true);
    return sysLogDO;
  }

  /**
   * DO转为VO
   *
   * @param sysLog 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.SysLogVO
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
   * @param sysLogs 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.SysLogVO
   */
  public static List<SysLogVO> convertTo(List<SysLogDO> sysLogs) {
    return sysLogs.stream()
        .map(SysLogConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.module.log.domain.vo.SysLogVO
   */
  public static Page<SysLogVO> convertTo(Page<SysLogDO> page) {
    return page.map(SysLogConverter::convertTo);
  }
}