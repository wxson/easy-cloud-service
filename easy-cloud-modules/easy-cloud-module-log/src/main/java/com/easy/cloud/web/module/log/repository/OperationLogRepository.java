package com.easy.cloud.web.module.log.repository;

import com.easy.cloud.web.module.log.domain.OperationLogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OperationLog持久化
 *
 * @author Fast Java
 * @date 2023-08-08 11:46:41
 */
@Repository
public interface OperationLogRepository extends JpaRepository<OperationLogDO, Long> {

}