package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.RelationRolePermissionDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author GR
 * @date 2023/8/3 17:57
 */
@Repository
public interface RelationRolePermissionRepository extends
    JpaRepository<RelationRolePermissionDO, Long> {

  /**
   * 根据角色ID删除数据
   *
   * @param roleId 角色ID
   */
  void deleteByRoleId(Long roleId);


  /**
   * 根据角色ID获取菜单信息
   *
   * @param roleIds 角色ID
   * @return
   */
  List<RelationRolePermissionDO> findByRoleIdIn(List<Long> roleIds);


}
