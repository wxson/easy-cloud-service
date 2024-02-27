package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.RoleDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Role持久化
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Repository
public interface PlatformRoleRepository extends JpaLogicRepository<RoleDO, String> {

    /**
     * 根据编码批量获取角色信息
     *
     * @param codes 角色编码集合
     * @return
     */
    List<RoleDO> findAllByCodeIn(List<String> codes);
}