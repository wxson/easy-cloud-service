package com.easy.cloud.web.module.certification.biz.repository;

import com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CompanyAuthentication持久化
 *
 * @author Fast Java
 * @date 2024-02-18 11:58:57
 */
@Repository
public interface CompanyAuthenticationRepository extends JpaRepository<CompanyAuthenticationDO, String> {

    /**
     * 根据统一社会信用代码获取企业认证信息
     * @param usci 统一社会信用代码
     * @return java.util.Optional<com.easy.cloud.web.module.certification.biz.domain.CompanyAuthenticationDO>
     */
    Optional<CompanyAuthenticationDO> findByUsci(String usci);
}