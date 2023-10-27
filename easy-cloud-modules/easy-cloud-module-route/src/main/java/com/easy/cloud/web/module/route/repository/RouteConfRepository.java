package com.easy.cloud.web.module.route.repository;

import com.easy.cloud.web.module.route.domain.RouteConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author GR
 * @Entity com.easy.cloud.web.module.route..RouteConf
 */
@Repository
public interface RouteConfRepository extends JpaRepository<RouteConf, String> {


}
