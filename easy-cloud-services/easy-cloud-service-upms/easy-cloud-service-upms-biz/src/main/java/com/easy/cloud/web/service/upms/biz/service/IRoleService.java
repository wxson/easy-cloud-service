package com.easy.cloud.web.service.upms.biz.service;

import com.easy.cloud.web.service.upms.api.dto.RoleDTO;
import com.easy.cloud.web.service.upms.api.dto.RolePermissionDTO;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Role interface
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
public interface IRoleService {

  /**
   * 新增数据
   *
   * @param roleDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.RoleVO
   */
  RoleVO save(RoleDTO roleDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param roleDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.RoleVO
   */
  RoleVO update(RoleDTO roleDTO);

  /**
   * 根据ID删除数据
   *
   * @param roleId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(Long roleId);

  /**
   * 根据ID获取详情
   *
   * @param roleId 对象ID
   * @return java.lang.Boolean
   */
  RoleVO detailById(Long roleId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.RoleVO> 返回列表数据
   */
  List<RoleVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.api.vo.RoleVO> 返回列表数据
   */
  Page<RoleVO> page(int page, int size);

  /**
   * 绑定角色权限
   *
   * @param rolePermissionDTO 角色权限数据
   * @return
   */
  RoleVO bindRolePermission(RolePermissionDTO rolePermissionDTO);
}