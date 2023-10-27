package com.easy.cloud.web.module.log.repository;

import com.easy.cloud.web.module.log.domain.SysLogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * OperationLog持久化
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Repository
public interface SysLogRepository extends JpaRepository<SysLogDO, Long>,
    JpaSpecificationExecutor<SysLogDO> {

}