package com.easy.cloud.web.module.certification.biz.repository;

import com.easy.cloud.web.module.certification.biz.domain.PersonalAuthenticationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PersonalAuthentication持久化
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Repository
public interface PersonalAuthenticationRepository extends JpaRepository<PersonalAuthenticationDO, String> {

}