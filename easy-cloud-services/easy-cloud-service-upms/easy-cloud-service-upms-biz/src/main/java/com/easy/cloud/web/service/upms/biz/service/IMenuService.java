package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;

/**
 * Menu interface
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
public interface IMenuService extends IInitService {

  /**
   * 新增数据
   *
   * @param menuDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.MenuVO
   */
  MenuVO save(MenuDTO menuDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param menuDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.MenuVO
   */
  MenuVO update(MenuDTO menuDTO);

  /**
   * 根据ID删除数据
   *
   * @param menuId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(String menuId);

  /**
   * 根据ID获取详情
   *
   * @param menuId 对象ID
   * @return java.lang.Boolean
   */
  MenuVO detailById(String menuId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.MenuVO> 返回列表数据
   */
  List<MenuVO> list();

  /**
   * 获取用户权限：此权限仅限于按钮权限
   *
   * @param roleIds 角色ID
   * @return
   */
  Set<String> findPermissionsByRoleIds(List<String> roleIds);

  /**
   * 获取用户权限：此权限仅限于按钮权限
   *
   * @param roleCodes 角色ID
   * @return
   */
  Set<String> findPermissionsByRoleCodes(ArrayList<String> roleCodes);

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.api.vo.MenuVO> 返回列表数据
   */
  Page<MenuVO> page(int page, int size);

  /**
   * 获取用户菜单树
   *
   * @param parentId 菜单根目录
   * @param channels 用户角色编码
   * @return
   */
  List<Tree<String>> findUserMenus(String parentId, List<String> channels);

  /**
   * 获取角色下的所有权限菜单
   *
   * @param roleId 角色ID
   * @return
   */
  List<String> findRoleMenus(String roleId);
}