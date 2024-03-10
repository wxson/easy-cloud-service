package com.easy.cloud.web.module.protocol.biz.repository;

import com.easy.cloud.web.module.protocol.biz.domain.ProtocolDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Protocol 持久化
 *
 * @author Fast Java
 * @date 2024-01-20 10:59:08
 */
@Repository
public interface ProtocolRepository extends JpaRepository<ProtocolDO, String> {

    /**
     * 协议唯一标识
     *
     * @param uniqueId 唯一标识
     * @return com.easy.cloud.web.service.cms.biz.domain.ProtocolDO
     */
    ProtocolDO findByUniqueId(String uniqueId);
}