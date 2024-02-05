package com.easy.cloud.web.service.order.biz.repository;

import com.easy.cloud.web.service.order.biz.domain.OrderRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * OrderRecord持久化
 *
 * @author Fast Java
 * @date 2024-02-05 10:51:03
 */
@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecordDO, String>, JpaSpecificationExecutor<OrderRecordDO> {

}