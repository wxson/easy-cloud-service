package com.easy.cloud.web.service.order.biz.repository;

import com.easy.cloud.web.service.order.biz.domain.InvoiceRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * InvoiceRecord持久化
 *
 * @author Fast Java
 * @date 2024-02-05 17:55:53
 */
@Repository
public interface InvoiceRecordRepository extends JpaRepository<InvoiceRecordDO, String> {

}