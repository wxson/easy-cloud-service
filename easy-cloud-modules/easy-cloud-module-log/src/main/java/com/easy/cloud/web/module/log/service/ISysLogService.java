package com.easy.cloud.web.module.log.service;

import com.easy.cloud.web.service.upms.api.dto.SysLogDTO;
import com.easy.cloud.web.service.upms.api.vo.SysLogVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * OperationLog interface
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
public interface ISysLogService {

  /**
   * 新增数据
   *
   * @param sysLogDTO 保存参数
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  SysLogVO save(SysLogDTO sysLogDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param sysLogDTO 保存参数
   * @return com.easy.cloud.web.module.log.domain.vo.OperationLogVO
   */
  SysLogVO update(SysLogDTO sysLogDTO);

  /**
   * 根据ID删除数据
   *
   * @param operationLogId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(Long operationLogId);

  /**
   * 根据ID获取详情
   *
   * @param operationLogId 对象ID
   * @return java.lang.Boolean
   */
  SysLogVO detailById(Long operationLogId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.module.log.domain.vo.OperationLogVO> 返回列表数据
   */
  List<SysLogVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.module.log.domain.vo.OperationLogVO> 返回列表数据
   */
  Page<SysLogVO> page(int page, int size);
}