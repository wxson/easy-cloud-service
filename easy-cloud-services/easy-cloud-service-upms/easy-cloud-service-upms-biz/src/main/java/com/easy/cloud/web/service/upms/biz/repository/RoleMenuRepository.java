package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.service.upms.biz.domain.RoleMenuDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author GR
 * @date 2023/8/3 17:57
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenuDO, String> {

  /**
   * 根据角色ID删除数据
   *
   * @param roleId 角色ID
   */
  void deleteByRoleId(String roleId);

  /**
   * 根据菜单ID删除数据
   *
   * @param menuId 菜单ID
   */
  void deleteByMenuId(String menuId);


  /**
   * 根据角色编码获取菜单信息
   *
   * @param roleIds 角色ID
   * @return
   */
  List<RoleMenuDO> findByRoleIdIn(List<String> roleIds);
}
