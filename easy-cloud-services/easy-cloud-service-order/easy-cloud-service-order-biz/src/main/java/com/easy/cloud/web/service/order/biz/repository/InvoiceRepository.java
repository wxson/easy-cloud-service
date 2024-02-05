package com.easy.cloud.web.service.order.biz.repository;

import com.easy.cloud.web.service.order.biz.domain.InvoiceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Invoice持久化
 *
 * @author Fast Java
 * @date 2024-02-05 17:28:27
 */
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceDO, String> {

}