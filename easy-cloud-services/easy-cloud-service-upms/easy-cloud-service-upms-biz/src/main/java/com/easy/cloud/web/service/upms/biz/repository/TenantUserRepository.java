package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User持久化
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Repository
public interface TenantUserRepository extends JpaLogicRepository<UserDO, String> {

    /**
     * 冻结租户下所有用户信息
     *
     * @param tenantId 租户信息
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "UPDATE db_user table SET table.deleted = 'DELETED' WHERE table.tenant_id = ?1", nativeQuery = true)
    void logicDeleteTenantAllUser(String tenantId);

    /**
     * 冻结租户下所有用户信息
     *
     * @param tenantId 租户信息
     * @param status   用户状态
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "UPDATE db_user table SET table.status = ?2 WHERE table.tenant_id = ?1", nativeQuery = true)
    void updateTenantAllUser(String tenantId, String status);
}