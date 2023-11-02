package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import com.easy.cloud.web.service.upms.biz.converter.MenuConverter;
import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import com.easy.cloud.web.service.upms.biz.domain.RoleMenuDO;
import com.easy.cloud.web.service.upms.biz.repository.MenuRepository;
import com.easy.cloud.web.service.upms.biz.repository.RoleMenuRepository;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import com.easy.cloud.web.service.upms.biz.utils.MenuInitUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Menu 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-03 14:45:40
 */
@Slf4j
@Service
public class MenuServiceImpl implements IMenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private RoleMenuRepository roleMenuRepository;

  @Autowired
  private IRoleService roleService;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void init() {
    // 未初始化过数据
    if (menuRepository.count() <= 0) {
      List<MenuDO> menus = MenuInitUtil.initSystemDefaultMenus();
      // 存储菜单信息
      menuRepository.saveAll(menus);
      // 根据父级菜单获取子菜单数据
      this.initChildrenMenu("system", menus);
      // 根据父级菜单获取子菜单数据
      this.initChildrenMenu("systemUser", menus);
      // 根据父级菜单获取子菜单数据
      this.initChildrenMenu("systemRole", menus);
      // 根据父级菜单获取子菜单数据
      this.initChildrenMenu("systemMenu", menus);
      log.info("init platform menus content success!");
    }
  }

  /**
   * 初始化子菜单数据
   *
   * @param parentName 父级菜单名称
   * @param menus      菜单列表
   */
  private void initChildrenMenu(String parentName, List<MenuDO> menus) {
    // 根据父级菜单获取子菜单数据
    List<MenuDO> menuChildrenMenus = menus.stream()
        .filter(childrenMenu -> parentName.equals(childrenMenu.getParentId()))
        .collect(Collectors.toList());
    // 修改系统菜单层级
    menus.stream()
        .filter(menuDO -> parentName.equals(menuDO.getName()))
        .findFirst()
        .ifPresent(menuDO -> {
          for (MenuDO childrenMenu : menuChildrenMenus) {
            childrenMenu.setParentId(menuDO.getId());
          }
          menuRepository.saveAll(menuChildrenMenus);
        });
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public MenuVO save(MenuDTO menuDTO) {
    // 转换成DO对象
    MenuDO menu = MenuConverter.convertTo(menuDTO);
    // TODO 校验逻辑

    // 存储
    menuRepository.save(menu);
    // 转换对象
    return MenuConverter.convertTo(menu);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public MenuVO update(MenuDTO menuDTO) {
    // 更新操作，ID不能为空
    if (Objects.isNull(menuDTO.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // 若父级ID为空，则设置默认父级ID
    if (StrUtil.isBlank(menuDTO.getParentId())) {
      menuDTO.setParentId(GlobalCommonConstants.DEPART_TREE_ROOT_ID);
    }
    // 获取当前菜单信息
    MenuDO menuDO = menuRepository.findById(menuDTO.getId())
        .orElseThrow(() -> new BusinessException("当前菜单信息不存在"));
    // 将修改的数据赋值给数据库数据
    BeanUtils.copyProperties(menuDTO, menuDO, true);
    // 存储
    menuRepository.save(menuDO);
    // 转换对象
    return MenuConverter.convertTo(menuDO);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public Boolean removeById(String menuId) {
    // 判断当前菜单下是否存在子菜单
    if (menuRepository.countByParentId(menuId) > 0) {
      throw new BusinessException("当前菜单下存在子菜单，请先删除子菜单");
    }
    // 获取当前菜单信息，可以不删除关联表，因为前端展示的一般为主体信息，主体信息已删除，那么关联表意义就不大，后续关联表恢复数据也很重要
    menuRepository.logicDelete(menuId);
    return true;
  }

  @Override
  public MenuVO detailById(String menuId) {
    // TODO 业务逻辑校验

    // 删除
    MenuDO menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return MenuConverter.convertTo(menu);
  }

  @Override
  public List<MenuVO> list() {
    // 获取列表数据
    List<MenuDO> menus = menuRepository.findAll();
    return MenuConverter.convertTo(menus);
  }

  @Override
  public Set<String> findPermissionsByRoleIds(List<String> roleIds) {
    // 否则根据权限获取数据
    List<String> menuIds = roleMenuRepository.findByRoleIdIn(roleIds).stream()
        .map(RoleMenuDO::getMenuId)
        .distinct()
        .collect(Collectors.toList());
    // 根据ID获取菜单权限
    return menuRepository.findAllById(menuIds)
        .stream()
        .filter(menuDO -> MenuTypeEnum.BUTTON == menuDO.getType())
        .map(MenuDO::getPerms)
        .collect(Collectors.toSet());
  }


  @Override
  public Set<String> findPermissionsByRoleCodes(ArrayList<String> roleCodes) {
    // 若包含超管，则获取所有
    if (roleCodes.contains(GlobalCommonConstants.SUPER_ADMIN_ROLE)) {
      return menuRepository.findAll()
          .stream()
          .filter(menuDO -> MenuTypeEnum.BUTTON == menuDO.getType())
          .map(MenuDO::getPerms)
          .collect(Collectors.toSet());
    }
    // 根据角色编码获取所有角色ID
    List<String> roleIds = roleService.findAllByCodes(roleCodes)
        .stream()
        .map(RoleVO::getId)
        .distinct()
        .collect(Collectors.toList());
    // 根据ID获取菜单权限
    return this.findPermissionsByRoleIds(roleIds);
  }


  @Override
  public Page<MenuVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return MenuConverter.convertTo(menuRepository.findAll(pageable));
  }

  @Override
  public List<Tree<String>> findUserMenus(String parentId, List<String> channels) {
    // 菜单集合
    List<MenuVO> menus = new ArrayList<>();
    // 如果是超级管理员，拥有所有权限
    if (channels.contains(GlobalCommonConstants.SUPER_ADMIN_ROLE)) {
      menus.addAll(menuRepository.findAll().stream()
          .map(menuDO -> menuDO.convertTo(MenuVO.class))
          .collect(Collectors.toList())
      );
    } else {
      // 根据角色编码获取所有角色ID
      List<String> roleIds = roleService.findAllByCodes(channels)
          .stream()
          .map(RoleVO::getId)
          .collect(Collectors.toList());
      // 根据角色ID获取菜单ID
      List<String> menuIds = roleMenuRepository.findByRoleIdIn(roleIds).stream()
          .map(RoleMenuDO::getMenuId)
          .collect(Collectors.toList());
      // 根据菜单ID获取所有菜单
      menus.addAll(menuRepository.findAllById(menuIds).stream()
          .map(menuDO -> menuDO.convertTo(MenuVO.class))
          .collect(Collectors.toList()));
    }
    return TreeUtil
        .build(menus, GlobalCommonConstants.MENU_TREE_ROOT_ID, (menu, tree) -> {
          tree.setId(menu.getId());
          tree.setName(menu.getName());
          tree.setParentId(menu.getParentId());
          tree.setWeight(menu.getSort());
          tree.putAll(BeanUtil.beanToMap(menu));
        });
  }

  @Override
  public List<String> findRoleMenus(String roleId) {
    // 根据角色ID获取菜单ID
    List<String> menuIds = roleMenuRepository.findAllByRoleId(roleId)
        .stream()
        .map(RoleMenuDO::getMenuId)
        .collect(Collectors.toList());
    // 根据菜单ID获取所有菜单
    return menuRepository.findAllById(menuIds).stream()
        .map(MenuDO::getId)
        .distinct()
        .collect(Collectors.toList());
  }
}