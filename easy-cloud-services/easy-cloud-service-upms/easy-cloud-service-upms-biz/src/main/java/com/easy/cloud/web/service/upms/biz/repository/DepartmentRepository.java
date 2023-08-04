package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.DepartmentDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Department持久化
 *
 * @author Fast Java
 * @date 2023-08-03 15:00:02
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentDO, Long> {

}