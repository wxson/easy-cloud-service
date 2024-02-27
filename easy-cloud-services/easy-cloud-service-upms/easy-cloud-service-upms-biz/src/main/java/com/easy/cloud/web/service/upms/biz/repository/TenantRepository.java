package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.TenantDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Tenant持久化
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Repository
public interface TenantRepository extends JpaLogicRepository<TenantDO, String> {

    /**
     * 更新租户信息
     *
     * @param tenantId 租户ID
     * @param status   租户状态
     */
    @Modifying
    @Query("UPDATE #{#entityName} table SET table.status = ?2 WHERE table.id = ?1")
    void updateTenantStatus(String tenantId, StatusEnum status);
}