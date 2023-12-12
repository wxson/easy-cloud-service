package com.easy.cloud.web.module.sms.biz.repository;

import com.easy.cloud.web.module.sms.biz.domain.SmsDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Sms持久化
 *
 * @author Fast Java
 * @date 2023-12-11 18:43:48
 */
@Repository
public interface SmsRepository extends JpaRepository<SmsDO, Long> {

}