package com.easy.cloud.web.service.upms.biz.converter;

import cn.hutool.core.date.DateUtil;
import com.easy.cloud.web.component.core.constants.DateTimeConstants;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.TenantDTO;
import com.easy.cloud.web.service.upms.api.vo.TenantVO;
import com.easy.cloud.web.service.upms.biz.domain.TenantDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * Tenant转换器
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
public class TenantConverter {

  /**
   * DTO转为DO
   *
   * @param tenant 转换数据
   * @return com.easy.cloud.web.service.upms.biz.domain.db.TenantDO
   */
  public static TenantDO convertTo(TenantDTO tenant) {
    TenantDO tenantDO = TenantDO.builder().build();
    BeanUtils.copyProperties(tenant, tenantDO, true);
    return tenantDO;
  }

  /**
   * DO转为VO
   *
   * @param tenant 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.TenantVO
   */
  public static TenantVO convertTo(TenantDO tenant) {
    TenantVO tenantVO = TenantVO.builder().build();
    BeanUtils.copyProperties(tenant, tenantVO, true);
    return tenantVO;
  }

  /**
   * 列表DO转为VO
   *
   * @param tenants 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.TenantVO
   */
  public static List<TenantVO> convertTo(List<TenantDO> tenants) {
    return tenants.stream()
        .map(TenantConverter::convertTo)
        .collect(Collectors.toList());
  }

  /**
   * 分页DO转为VO
   *
   * @param page 转换数据
   * @return com.easy.cloud.web.service.upms.api.vo.TenantVO
   */
  public static Page<TenantVO> convertTo(Page<TenantDO> page) {
    return page.map(TenantConverter::convertTo);
  }
}