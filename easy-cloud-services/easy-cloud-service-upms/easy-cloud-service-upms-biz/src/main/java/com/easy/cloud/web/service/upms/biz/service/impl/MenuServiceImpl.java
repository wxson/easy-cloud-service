package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.easy.cloud.web.service.upms.api.dto.MenuDTO;
import com.easy.cloud.web.service.upms.api.enums.MenuTypeEnum;
import com.easy.cloud.web.service.upms.api.enums.RoleEnum;
import com.easy.cloud.web.service.upms.api.vo.MenuVO;
import com.easy.cloud.web.service.upms.biz.constant.UpmsConstants;
import com.easy.cloud.web.service.upms.biz.converter.MenuConverter;
import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import com.easy.cloud.web.service.upms.biz.domain.RelationRolePermissionDO;
import com.easy.cloud.web.service.upms.biz.repository.MenuRepository;
import com.easy.cloud.web.service.upms.biz.repository.RelationRolePermissionRepository;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import com.easy.cloud.web.service.upms.biz.utils.MenuInitUtil;
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
  private RelationRolePermissionRepository relationRolePermissionRepository;

  @Override
  public void init() {
    // 未初始化过数据
    if (menuRepository.count() <= 0) {
      List<MenuDO> menus = MenuInitUtil.initSystemDefaultMenus();
      // 存储菜单信息
      menuRepository.saveAll(menus);
      List<RelationRolePermissionDO> relationRolePermissionDOS = menus.stream().map(MenuDO::getId)
          .map(menuId -> RelationRolePermissionDO.builder()
              .menuId(menuId)
              .roleId(RoleEnum.ROLE_SUPER_ADMIN.getId())
              .build())
          .collect(Collectors.toList());
      // 菜单权限分配给超管
      relationRolePermissionRepository.saveAll(relationRolePermissionDOS);
      log.info("init platform menus content success!");
    }
  }


  @Override
  @Transactional
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
  @Transactional
  public MenuVO update(MenuDTO menuDTO) {
    // 转换成DO对象
    MenuDO menu = MenuConverter.convertTo(menuDTO);
    if (Objects.isNull(menu.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    menuRepository.save(menu);
    // 转换对象
    return MenuConverter.convertTo(menu);
  }

  @Override
  @Transactional
  public Boolean removeById(Long menuId) {
    // TODO 业务逻辑校验

    // 删除
    menuRepository.deleteById(menuId);
    return true;
  }

  @Override
  public MenuVO detailById(Long menuId) {
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
  public Page<MenuVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return MenuConverter.convertTo(menuRepository.findAll(pageable));
  }

  @Override
  public List<Tree<Long>> findUserMenus(MenuTypeEnum type, Long parentId, List<Long> userRoles) {
    // 获取角色范围内的所有菜单ID
    List<Long> menuIds = relationRolePermissionRepository.findByRoleIdIn(userRoles).stream()
        .map(RelationRolePermissionDO::getMenuId)
        .collect(Collectors.toList());
    // 获取所有菜单
    List<MenuVO> menus = menuRepository.findAllById(menuIds).stream()
        .filter(menuDO -> menuDO.getType() == MenuTypeEnum.MENU)
        .map(menuDO -> menuDO.convertTo(MenuVO.class))
        .collect(Collectors.toList());
    return TreeUtil
        .build(menus, UpmsConstants.MENU_TREE_ROOT_ID, (menu, tree) -> {
          Map<String, Object> menuMap = BeanUtil.beanToMap(menu);
          tree.setId(menu.getId());
          tree.setName(menu.getName());
          tree.setParentId(menu.getParentId());
          tree.putAll(menuMap);
        });
  }
}