package com.easy.cloud.web.module.certification.biz.repository;

import com.easy.cloud.web.module.certification.biz.domain.UserAuthenticationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PersonalAuthentication持久化
 *
 * @author Fast Java
 * @date 2024-02-18 11:55:13
 */
@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthenticationDO, String> {

}