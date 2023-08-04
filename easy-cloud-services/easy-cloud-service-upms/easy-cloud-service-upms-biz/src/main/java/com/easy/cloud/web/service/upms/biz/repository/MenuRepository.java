package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Menu持久化
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuDO, Long> {

}