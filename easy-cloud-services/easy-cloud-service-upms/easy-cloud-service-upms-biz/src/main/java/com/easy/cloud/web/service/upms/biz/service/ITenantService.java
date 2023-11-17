package com.easy.cloud.web.service.upms.biz.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.service.upms.api.dto.TenantDTO;
import com.easy.cloud.web.service.upms.api.vo.TenantVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Tenant interface
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
public interface ITenantService extends IInitService {

  /**
   * 新增数据
   *
   * @param tenantDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.TenantVO
   */
  TenantVO save(TenantDTO tenantDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param tenantDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.TenantVO
   */
  TenantVO update(TenantDTO tenantDTO);

  /**
   * 根据ID删除数据
   *
   * @param tenantId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(String tenantId);

  /**
   * 冻结租户信息
   *
   * @param tenantId 对象ID
   * @return java.lang.Boolean
   */
  TenantVO freezeTenant(String tenantId);

  /**
   * 根据ID获取详情
   *
   * @param departmentId 对象ID
   * @return java.lang.Boolean
   */
  TenantVO detailById(String departmentId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.TenantVO> 返回列表数据
   */
  List<TenantVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.api.vo.TenantVO> 返回列表数据
   */
  Page<TenantVO> page(int page, int size);

}