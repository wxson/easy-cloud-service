package com.easy.cloud.web.service.upms.biz.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.service.upms.api.dto.RoleDTO;
import com.easy.cloud.web.service.upms.api.dto.RoleMenuDTO;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Role interface
 *
 * @author Fast Java
 * @date 2023-08-03 14:32:52
 */
public interface IRoleService extends IInitService {

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
  Boolean removeById(String roleId);

  /**
   * 根据ID获取详情
   *
   * @param roleId 对象ID
   * @return java.lang.Boolean
   */
  RoleVO detailById(String roleId);

  /**
   * 根据角色编码获取角色信息
   * @param code 角色编码
   * @return
   */
  RoleVO findFirstByCode(String code);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.RoleVO> 返回列表数据
   */
  List<RoleVO> list();

  /**
   * 根据角色ID获取角色信息
   *
   * @param roleIds 角色ID
   * @return
   */
  List<RoleVO> findAllByIds(List<String> roleIds);

  /**
   * 根据角色编码获取角色信息
   *
   * @param roleCodes 角色编码
   * @return
   */
  List<RoleVO> findAllByCodes(List<String> roleCodes);

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
   * @param roleMenuDTO 角色权限数据
   * @return
   */
  RoleVO bindRoleMenu(RoleMenuDTO roleMenuDTO);
}