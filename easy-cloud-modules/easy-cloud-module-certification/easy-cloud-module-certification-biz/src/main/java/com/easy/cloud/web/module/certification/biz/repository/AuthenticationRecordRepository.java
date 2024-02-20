package com.easy.cloud.web.module.certification.biz.repository;

import com.easy.cloud.web.module.certification.biz.domain.AuthenticationRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthenticationRecord持久化
 *
 * @author Fast Java
 * @date 2024-02-19 11:57:45
 */
@Repository
public interface AuthenticationRecordRepository extends JpaRepository<AuthenticationRecordDO, String> {

}