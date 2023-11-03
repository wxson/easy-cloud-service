package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.enums.DeletedEnum;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.service.upms.api.dto.RoleDTO;
import com.easy.cloud.web.service.upms.api.dto.RoleMenuDTO;
import com.easy.cloud.web.service.upms.api.enums.RoleEnum;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import com.easy.cloud.web.service.upms.biz.constant.UpmsCacheConstants;
import com.easy.cloud.web.service.upms.biz.converter.RoleConverter;
import com.easy.cloud.web.service.upms.biz.domain.RoleDO;
import com.easy.cloud.web.service.upms.biz.domain.RoleMenuDO;
import com.easy.cloud.web.service.upms.biz.repository.RoleMenuRepository;
import com.easy.cloud.web.service.upms.biz.repository.RoleRepository;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Role 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private RoleMenuRepository roleMenuRepository;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void init() {
    // 未初始化过数据
    if (roleRepository.count() <= 0) {
      List<RoleDO> roleDOS = Arrays.stream(RoleEnum.values())
          .map(roleEnum -> RoleDO.builder()
              .code(roleEnum.getCode())
              .remark(roleEnum.getDesc())
              .name(roleEnum.getDesc())
              .tenantId(GlobalCommonConstants.DEFAULT_TENANT)
              .deleted(DeletedEnum.UN_DELETED)
              .status(StatusEnum.START_STATUS)
              .build()).collect(Collectors.toList());
      // 初始化存储
      roleRepository.saveAll(roleDOS);
      log.info("init platform roles content success!");
    }
  }

  @Override
  @Transactional
  public RoleVO save(RoleDTO roleDTO) {
    // 转换成DO对象
    RoleDO role = RoleConverter.convertTo(roleDTO);
    // TODO 校验逻辑

    // 存储
    roleRepository.save(role);
    // 更新角色权限信息:先删除，后添加
    this.updateRoleMenu(role, roleDTO);
    // 转换对象
    return RoleConverter.convertTo(role);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  @CacheEvict(value = UpmsCacheConstants.SUPER_ROLE_DETAILS, allEntries = true)
  public RoleVO update(RoleDTO roleDTO) {
    // 转换成DO对象
    if (Objects.isNull(roleDTO.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验
    RoleDO role = roleRepository.findById(roleDTO.getId())
        .orElseThrow(() -> new BusinessException("当前菜单信息不存在"));
    // 将修改的数据赋值给数据库数据
    BeanUtils.copyProperties(roleDTO, role, true);
    // 更新
    roleRepository.save(role);
    // 更新角色权限信息:先删除，后添加
    this.updateRoleMenu(role, roleDTO);

    // 转换对象
    return RoleConverter.convertTo(role);
  }

  /**
   * 更新角色权限信息:先删除，后添加
   *
   * @param roleDO  角色信息
   * @param roleDTO 绑定信息
   */
  private void updateRoleMenu(RoleDO roleDO, RoleDTO roleDTO) {
    // 更新角色权限信息:先删除，后添加
    roleMenuRepository.deleteByRoleId(roleDO.getId());
    if (CollUtil.isNotEmpty(roleDTO.getMenuIds())) {
      List<RoleMenuDO> roleMenus = roleDTO.getMenuIds().stream()
          .map(menuId -> RoleMenuDO.builder()
              .roleId(roleDO.getId())
              .menuId(menuId)
              .build())
          .collect(Collectors.toList());
      roleMenuRepository.saveAll(roleMenus);
    }
  }

  @Override
  @Transactional
  @CacheEvict(value = UpmsCacheConstants.SUPER_ROLE_DETAILS, allEntries = true)
  public Boolean removeById(String roleId) {
    // TODO 业务逻辑校验
    // 逻辑删除操作
    roleRepository.logicDelete(roleId);
    return true;
  }

  @Override
  public RoleVO detailById(String roleId) {
    // TODO 业务逻辑校验

    // 删除
    RoleDO role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换对象
    return RoleConverter.convertTo(role);
  }

  @Override
  @Cacheable(value = UpmsCacheConstants.SUPER_ROLE_DETAILS, key = "#code")
  public RoleVO findFirstByCode(String code) {
    return RoleConverter
        .convertTo(roleRepository.findFirstByCodeAndDeleted(code, DeletedEnum.UN_DELETED));
  }

  @Override
  public List<RoleVO> list() {
    // 获取列表数据
    List<RoleDO> roles = roleRepository.findAll();
    return RoleConverter.convertTo(roles);
  }

  @Override
  public List<RoleVO> findAllByIds(List<String> roleIds) {
    return roleRepository.findAllById(roleIds)
        .stream()
        .map(RoleConverter::convertTo)
        .collect(Collectors.toList());
  }

  @Override
  public List<RoleVO> findAllByCodes(List<String> roleCodes) {
    return roleRepository.findAllByCodeInAndDeleted(roleCodes, DeletedEnum.UN_DELETED)
        .stream()
        .map(RoleConverter::convertTo)
        .collect(Collectors.toList());
  }

  @Override
  public Page<RoleVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
    return RoleConverter.convertTo(roleRepository.findAll(pageable));
  }

  @Override
  public RoleVO bindRoleMenu(RoleMenuDTO roleMenuDTO) {
    // 根据ID获取用户信息
    Optional<RoleDO> roleDOOptional = roleRepository.findById(roleMenuDTO.getRoleId());
    if (!roleDOOptional.isPresent()) {
      throw new BusinessException("当前角色信息不存在");
    }

    // 获取菜单列表
    List<String> menuIds = roleMenuDTO.getMenuIds();
    if (CollUtil.isEmpty(menuIds)) {
      return roleDOOptional.get().convertTo(RoleVO.class);
    }

    // 移除旧数据
    roleMenuRepository.deleteByRoleId(roleMenuDTO.getRoleId());
    // 添加新数据
    List<RoleMenuDO> relationRolePermissions = menuIds.stream().map(
        menuId -> RoleMenuDO.builder()
            .roleId(roleMenuDTO.getRoleId()).menuId(menuId).build())
        .collect(Collectors.toList());
    // 批量存储
    roleMenuRepository.saveAll(relationRolePermissions);
    RoleDO roleDO = roleDOOptional.get();
    RoleVO roleVO = roleDO.convertTo(RoleVO.class);
    roleVO.setMenuIds(menuIds);
    return roleVO;
  }
}