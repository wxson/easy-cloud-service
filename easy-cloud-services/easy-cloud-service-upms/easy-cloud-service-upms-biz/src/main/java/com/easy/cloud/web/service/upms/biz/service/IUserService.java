package com.easy.cloud.web.service.upms.biz.service;

import com.easy.cloud.web.component.core.service.IInitService;
import com.easy.cloud.web.service.upms.api.dto.UserBindDTO;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * User interface
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
public interface IUserService extends IInitService {

  /**
   * 新增数据
   *
   * @param userDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.UserVO
   */
  UserVO save(UserDTO userDTO);

  /**
   * 更新数据，默认全量更新
   *
   * @param userDTO 保存参数
   * @return com.easy.cloud.web.service.upms.api.vo.UserVO
   */
  UserVO update(UserDTO userDTO);

//  /**
//   * 创建租户管理员
//   *
//   * @param userDTO 租户信息
//   * @return
//   */
//  UserVO createTenantAdmin(UserDTO userDTO);
//
//  /**
//   * 更新租户管理员
//   *
//   * @param userDTO 租户信息
//   * @return
//   */
//  UserVO updateTenantAdmin(UserDTO userDTO);

  /**
   * 根据ID删除数据
   *
   * @param userId 对象ID
   * @return java.lang.Boolean
   */
  Boolean removeById(String userId);

  /**
   * 根据ID获取详情
   *
   * @param userId 对象ID
   * @return java.lang.Boolean
   */
  UserVO detailById(String userId);

  /**
   * 根据条件获取列表数据
   *
   * @return List<com.easy.cloud.web.service.upms.api.vo.UserVO> 返回列表数据
   */
  List<UserVO> list();

  /**
   * 根据条件获取分页数据
   *
   * @param page 当前页
   * @param size 每页大小
   * @return List<com.easy.cloud.web.service.upms.api.vo.UserVO> 返回列表数据
   */
  Page<UserVO> page(int page, int size);

  /**
   * 根据用户名获取详情
   *
   * @param userName 用户名
   * @return
   */
  UserVO loadUserByUsername(String userName);

  /**
   * Oauth2.0 code授权模式
   *
   * @param type         授权类型
   * @param userLoginDTO 授权数据
   * @return
   */
  UserVO loadSocialUser(String type, UserLoginDTO userLoginDTO);


  /**
   * 绑定用户角色
   *
   * @param userBindDTO 用户角色
   * @return
   */
  UserVO bindUserRole(UserBindDTO userBindDTO);

  /**
   * 注册用户
   *
   * @param userDto 用户信息
   * @return success/false
   */
  UserVO registerUser(UserDTO userDto);

  /**
   * 锁定用户
   *
   * @param userId
   * @return
   */
  UserVO lockUser(String userId);

  /**
   * 修改密码
   *
   * @param userBindDTO 用户信息
   * @return
   */
  void changePassword(UserBindDTO userBindDTO);

  /**
   * 校验密码
   *
   * @param userBindDTO 密码明文
   * @return
   */
  Boolean checkPassword(UserBindDTO userBindDTO);

  /**
   * 绑定用户电话
   *
   * @param userBindDTO
   */
  void bindUserTel(UserBindDTO userBindDTO);

  /**
   * 实名认证
   *
   * @param userBindDTO
   */
  void certification(UserBindDTO userBindDTO);
}