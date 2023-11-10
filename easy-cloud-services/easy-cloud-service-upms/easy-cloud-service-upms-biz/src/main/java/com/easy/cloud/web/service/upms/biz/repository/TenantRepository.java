package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.annotation.EnableTenant;
import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.TenantDO;
import org.springframework.stereotype.Repository;

/**
 * Tenant持久化
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@EnableLogic
@EnableTenant
@Repository
public interface TenantRepository extends JpaLogicRepository<TenantDO, String> {

}