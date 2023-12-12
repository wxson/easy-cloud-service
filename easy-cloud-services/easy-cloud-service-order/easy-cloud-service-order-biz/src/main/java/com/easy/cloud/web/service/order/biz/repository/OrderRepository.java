package com.easy.cloud.web.service.order.biz.repository;

import com.easy.cloud.web.service.order.biz.domain.OrderDO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Order持久化
 *
 * @author Fast Java
 * @date 2023-12-11 17:45:14
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderDO, String> {

  /**
   * 根据订单编码查询订单
   *
   * @param no 订单编码
   * @return
   */
  Optional<OrderDO> findByNo(String no);
}