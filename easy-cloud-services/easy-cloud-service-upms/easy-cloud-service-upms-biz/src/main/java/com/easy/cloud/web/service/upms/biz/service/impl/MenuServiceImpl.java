package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.core.util.FieldUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
      List<MenuDO> childrenMenus = menus.stream()
          .filter(childrenMenu -> "system".equals(childrenMenu.getParentId()))
          .collect(Collectors.toList());
      // 修改系统菜单层级
      menus.stream().filter(menuDO -> "system".equals(menuDO.getName()))
          .findFirst()
          .ifPresent(menuDO -> {
            for (MenuDO childrenMenu : childrenMenus) {
              childrenMenu.setParentId(menuDO.getId());
            }
            menuRepository.saveAll(childrenMenus);
          });

      // 获取超级管理员角色
      RoleVO superAdminRole = roleService.findFirstByCode(GlobalCommonConstants.SUPER_ADMIN_ROLE);
      // 修改菜单层级
      List<RoleMenuDO> roleMenuDOS = menus.stream().map(MenuDO::getId)
          .map(menuId -> RoleMenuDO.builder()
              .menuId(menuId)
              .roleId(superAdminRole.getId())
              .build())
          .collect(Collectors.toList());
      // 菜单权限分配给超管
      roleMenuRepository.saveAll(roleMenuDOS);
      log.info("init platform menus content success!");
    }
  }


  @Override
  @Transactional(rollbackOn = Exception.class)
  public MenuVO save(MenuDTO menuDTO) {
    // 转换成DO对象
    MenuDO menu = MenuConverter.convertTo(menuDTO);
    // TODO 校验逻辑

    // 存储
    menuRepository.save(menu);
    // 获取超管信息
    RoleVO superAdminRode = roleService.findFirstByCode(GlobalCommonConstants.SUPER_ADMIN_ROLE);
    // 赋予超级管理员权限
    RoleMenuDO roleMenuDO = RoleMenuDO.builder()
        .menuId(menu.getId())
        .roleId(superAdminRode.getId())
        .build();
    // 菜单权限分配给超管
    roleMenuRepository.save(roleMenuDO);
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
  public List<MenuVO> findAllByIds(List<String> menuIds) {
    return menuRepository.findAllById(menuIds)
        .stream()
        .map(MenuConverter::convertTo)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> findUserPermissions(List<String> menuIds) {
    return menuRepository.findAllById(menuIds)
        .stream()
        .filter(menuDO -> MenuTypeEnum.BUTTON == menuDO.getType())
        .map(MenuDO::getName)
        .collect(Collectors.toList());
  }

  @Override
  public Page<MenuVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return MenuConverter.convertTo(menuRepository.findAll(pageable));
  }

  @Override
  public List<Tree<String>> findUserMenus(MenuTypeEnum type, String parentId,
      List<String> channels) {
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
    List<MenuVO> menus = menuRepository.findAllById(menuIds).stream()
        .filter(menuDO -> menuDO.getType() == MenuTypeEnum.MENU)
        .map(menuDO -> menuDO.convertTo(MenuVO.class))
        .collect(Collectors.toList());
    return TreeUtil
        .build(menus, GlobalCommonConstants.MENU_TREE_ROOT_ID, (menu, tree) -> {
          // menu转为｛“name”:"",meta:{"title":""}｝格式
          Map<String, Object> metaMap = new HashMap<>();
          metaMap.put(FieldUtils.propertyName(MenuDO::getTitle), menu.getTitle());
          metaMap.put(FieldUtils.propertyName(MenuDO::getIsLink), menu.getIsLink());
          metaMap.put(FieldUtils.propertyName(MenuDO::getIsHidden), menu.getIsHidden());
          metaMap.put(FieldUtils.propertyName(MenuDO::getIsKeepAlive), menu.getIsKeepAlive());
          metaMap.put(FieldUtils.propertyName(MenuDO::getIsAffix), menu.getIsAffix());
          metaMap.put(FieldUtils.propertyName(MenuDO::getIsIframe), menu.getIsIframe());
          metaMap.put(FieldUtils.propertyName(MenuDO::getIcon), menu.getIcon());
          Map<String, Object> menuMap = new HashMap<>();
          menuMap.put("meta", metaMap);
          menuMap.put(FieldUtils.propertyName(MenuDO::getType), menu.getType());
          menuMap.put(FieldUtils.propertyName(MenuDO::getName), menu.getName());
          menuMap.put(FieldUtils.propertyName(MenuDO::getPath), menu.getPath());
          menuMap.put(FieldUtils.propertyName(MenuDO::getComponent), menu.getComponent());
          menuMap.put(FieldUtils.propertyName(MenuDO::getRedirect), menu.getRedirect());
          menuMap.put(FieldUtils.propertyName(MenuDO::getSort), menu.getSort());
          tree.setId(menu.getId());
          tree.setName(menu.getName());
          tree.setParentId(menu.getParentId());
          tree.setWeight(menu.getSort());
          tree.putAll(menuMap);
        });
  }
}