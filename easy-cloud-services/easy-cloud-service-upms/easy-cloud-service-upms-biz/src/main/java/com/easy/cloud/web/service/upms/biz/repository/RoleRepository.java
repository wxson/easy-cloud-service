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
public interface RoleRepository extends JpaLogicRepository<RoleDO, String> {

    /**
     * 根据角色编码获取角色信息
     *
     * @param code 角色编码
     * @return
     */
    RoleDO findFirstByCode(String code);

    /**
     * 根据角色编码获取橘色信息
     *
     * @param roleCodes 角色编码
     * @return
     */
    List<RoleDO> findAllByCodeIn(List<String> roleCodes);
}