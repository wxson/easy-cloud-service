package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.RoleDO;
import java.util.List;
import org.springframework.stereotype.Repository;

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
   * @param code    角色编码
   * @param deleted 是否删除
   * @return
   */
  RoleDO findFirstByCodeAndDeleted(String code, DeletedEnum deleted);

  /**
   * 根据角色编码获取橘色信息
   *
   * @param roleCodes 角色编码
   * @param deleted   是否删除
   * @return
   */
  List<RoleDO> findAllByCodeInAndDeleted(List<String> roleCodes, DeletedEnum deleted);
}