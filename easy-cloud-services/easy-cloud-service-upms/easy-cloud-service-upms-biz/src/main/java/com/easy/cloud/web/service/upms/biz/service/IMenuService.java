package com.easy.cloud.web.service.upms.biz.service;

import cn.hutool.core.lang.tree.Tree;
import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import java.util.List;
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
  Boolean removeById(Long menuId);

  /**
   * 根据ID获取详情
   *
   * @param menuId 对象ID
   * @return java.lang.Boolean
   */
  MenuVO detailById(Long menuId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.MenuVO> 返回列表数据
   */
  List<MenuVO> list();

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
   * @param type      菜单类型
   * @param parentId  菜单根目录
   * @param userRoles 用户角色
   * @return
   */
  List<Tree<Long>> findUserMenus(MenuTypeEnum type, Long parentId, List<Long> userRoles);
}