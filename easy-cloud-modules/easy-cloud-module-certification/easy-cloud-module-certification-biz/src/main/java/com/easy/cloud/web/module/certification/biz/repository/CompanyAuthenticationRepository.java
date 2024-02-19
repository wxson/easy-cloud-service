package com.easy.cloud.web.module.certification.biz.repository;

import com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CompanyAuthentication持久化
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Repository
public interface CompanyAuthenticationRepository extends JpaRepository<CompanyAuthenticationDO, String> {

}