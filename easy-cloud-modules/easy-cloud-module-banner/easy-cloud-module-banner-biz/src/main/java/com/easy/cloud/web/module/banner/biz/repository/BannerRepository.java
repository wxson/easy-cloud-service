package com.easy.cloud.web.module.banner.biz.repository;

import com.easy.cloud.web.module.banner.biz.domain.BannerDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Banner持久化
 *
 * @author Fast Java
 * @date 2024-03-02 23:43:57
 */
@Repository
public interface BannerRepository extends JpaRepository<BannerDO, String>, JpaSpecificationExecutor<BannerDO> {

}