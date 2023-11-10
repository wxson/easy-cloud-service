package com.easy.cloud.web.service.upms.biz.repository;

import com.easy.cloud.web.component.mysql.annotation.EnableLogic;
import com.easy.cloud.web.component.mysql.repository.JpaLogicRepository;
import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Menu持久化
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@EnableLogic
@Repository
public interface MenuRepository extends JpaLogicRepository<MenuDO, String> {

  /**
   * 根据父级ID获取子菜单
   *
   * @param parentId 父级ID
   * @return
   */
  List<MenuDO> findAllByParentId(String parentId);

  /**
   * 统计当前菜单下是否存在子菜单
   *
   * @param parentId 父级菜单
   * @return
   */
  long countByParentId(String parentId);
}