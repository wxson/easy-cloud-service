package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.api.dto.UserBindDTO;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.constant.UpmsCacheConstants;
import com.easy.cloud.web.service.upms.biz.converter.UserConverter;
import com.easy.cloud.web.service.upms.biz.domain.MenuDO;
import com.easy.cloud.web.service.upms.biz.domain.RelationRolePermissionDO;
import com.easy.cloud.web.service.upms.biz.domain.RelationUserRoleDO;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.repository.MenuRepository;
import com.easy.cloud.web.service.upms.biz.repository.RelationRolePermissionRepository;
import com.easy.cloud.web.service.upms.biz.repository.RelationUserRoleRepository;
import com.easy.cloud.web.service.upms.biz.repository.UserRepository;
import com.easy.cloud.web.service.upms.biz.service.IUserService;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService, ApplicationContextAware {

  private EnumMap<SocialTypeEnum, ISocialService> socialServices = new EnumMap<>(
      SocialTypeEnum.class);

//  @PersistenceContext
//  private EntityManager em;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private RelationUserRoleRepository relationUserRoleRepository;

  @Autowired
  private RelationRolePermissionRepository relationRolePermissionRepository;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    applicationContext.getBeansOfType(ISocialService.class).values()
        .forEach(socialService -> socialServices.put(socialService.getType(), socialService));
  }

  @Override
  @Transactional
  public UserVO save(UserDTO userDTO) {
    // 转换成DO对象
    UserDO user = UserConverter.convertTo(userDTO);
    // TODO 校验逻辑

    // 存储
    userRepository.save(user);
    // 转换对象
    return UserConverter.convertTo(user);
  }

  @Override
  @Transactional
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public UserVO update(UserDTO userDTO) {
    // 转换成DO对象
    UserDO user = UserConverter.convertTo(userDTO);
    if (Objects.isNull(user.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验

    // 更新
    userRepository.save(user);
    // 转换对象
    return UserConverter.convertTo(user);
  }

  @Override
  @Transactional
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public Boolean removeById(String userId) {
    // TODO 业务逻辑校验

    // 删除
    userRepository.deleteById(userId);
    return true;
  }

  @Override
  @Cacheable(value = UpmsCacheConstants.USER_DETAILS, key = "#userId")
  public UserVO detailById(String userId) {
    // TODO 业务逻辑校验

    // 删除
    UserDO user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("当前数据不存在"));
    // 转换
    UserVO userVO = this.findUserInfo(user);
    // 转换对象
    return userVO;
  }

  /**
   * 获取用户信息
   *
   * @param userDO
   * @return
   */
  private UserVO findUserInfo(UserDO userDO) {
    // 转换
    UserVO userVO = userDO.convertTo(UserVO.class);
    // 获取用户角色列表
    List<Long> roleIds = relationUserRoleRepository.findByUserId(userDO.getId()).stream()
        .map(RelationUserRoleDO::getRoleId)
        .collect(Collectors.toList());
    // 设置角色列表 （ID）
    userVO.setRoleIds(roleIds);

    // 获取所有关联的菜单ID
    List<Long> menuIds = relationRolePermissionRepository.findByRoleIdIn(roleIds).stream()
        .map(RelationRolePermissionDO::getMenuId)
        .collect(Collectors.toList());
    // 获取所有有关的菜单权限标识
    List<String> permissionTags = menuRepository.findAllById(menuIds).stream()
        .map(MenuDO::getPermissionTag)
        .filter(StringUtils::isNotBlank)
        .distinct()
        .collect(Collectors.toList());
    // 设置权限列表（menu.permission）
    userVO.setPermissionTags(permissionTags);
    return userVO;
  }

  @Override
  public List<UserVO> list() {
    // 获取列表数据
    List<UserDO> users = userRepository.findAll();
    return UserConverter.convertTo(users);
  }

  @Override
  public Page<UserVO> page(int page, int size) {
    // 构建分页数据
    Pageable pageable = PageRequest.of(page, size);
    return UserConverter.convertTo(userRepository.findAll(pageable));
  }

  @Override
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public UserVO bindUserRole(UserBindDTO userBindDTO) {
    // 根据ID获取用户信息
    Optional<UserDO> userDOOptional = userRepository.findById(userBindDTO.getId());
    if (!userDOOptional.isPresent()) {
      throw new BusinessException("当前用户信息不存在");
    }

    // 获取角色列表
    List<Long> roleIds = userBindDTO.getRoleIds();
    if (CollUtil.isEmpty(roleIds)) {
      return userDOOptional.get().convertTo(UserVO.class);
    }

    // 移除旧数据
    relationUserRoleRepository.deleteByUserId(userBindDTO.getId());
    // 添加新数据
    List<RelationUserRoleDO> relationUserRoles = roleIds.stream().map(
        roleId -> RelationUserRoleDO.builder().userId(userBindDTO.getId()).roleId(roleId).build())
        .collect(Collectors.toList());
    // 批量存储
    relationUserRoleRepository.saveAll(relationUserRoles);
    UserDO userDO = userDOOptional.get();
    UserVO userVO = userDO.convertTo(UserVO.class);
    userVO.setRoleIds(roleIds);
    return userVO;
  }

  @Override
  public UserVO loadUserByUsername(String userName) {
    // 若用户名为空，则返回
    if (StrUtil.isBlank(userName)) {
      throw new BusinessException("登录名不能为空");
    }
    // 用户名
    UserDO userDO = userRepository.findByAccount(userName);
    if (Objects.isNull(userDO)) {
      throw new BusinessException("当前用户不存在");
    }
    // 获取详情，走缓存
    return this.detailById(userDO.getId());
  }

  @Override
  public UserVO loadSocialUser(String type, UserLoginDTO userLoginDTO) {
    Optional<SocialTypeEnum> socialTypeEnumOptional = SocialTypeEnum.getSocialByType(type);
    if (socialTypeEnumOptional.isPresent()) {
      throw new BusinessException("登录类型错误");
    }
    // 授权类型
    SocialTypeEnum socialType = socialTypeEnumOptional.get();
    // 获取处理类
    ISocialService socialService = socialServices.get(socialType);
    UserDO userDO;
    // Oauth2.0 code授权模式
    if (StrUtil.isNotBlank(userLoginDTO.getCode())) {
      userDO = socialService.loadSocialUser(userLoginDTO.getCode());
    } else {
      userDO = JSONUtil.toBean(JSONUtil.toJsonStr(userLoginDTO), UserDO.class);
    }

    // 尝试获取是否已存在当前用户,后续新增其他平台授权，依次增加OR条件即可
    UserDO existUser = userRepository
        .findByUnionIdOOrAppleId(userDO.getUnionId(), userDO.getAppleId());
    if (Objects.isNull(existUser)) {
      // 赋值
      existUser = userDO;
      // 存储新的数据
      userRepository.save(existUser);
    }

    // 获取详情，走缓存
    return this.detailById(existUser.getId());
  }

  @Override
  public UserVO registerUser(UserDTO userDto) {
    if (StringUtils.isBlank(userDto.getAccount())) {
      throw new BusinessException("账号信息为空");
    }
    UserDO existUser = userRepository.findByAccount(userDto.getAccount());
    if (Objects.nonNull(existUser)) {
      throw new BusinessException("账号已存在");
    }
    // 新增
    return this.save(userDto);
  }

  @Override
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public UserVO lockUser(String userId) {
    Optional<UserDO> userDOOptional = userRepository.findById(userId);
    if (!userDOOptional.isPresent()) {
      throw new BusinessException("用户信息不存在");
    }
    UserDO userDO = userDOOptional.get();
    userDO.setStatus(StatusEnum.FREEZE_STATUS);
    userRepository.save(userDO);
    return userDO.convertTo(UserVO.class);
  }

  @Override
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public void changePassword(UserBindDTO userBindDTO) {
    Optional<UserDO> userDOOptional = userRepository.findById(userBindDTO.getId());
    if (!userDOOptional.isPresent()) {
      throw new BusinessException("用户不存在");
    }

    if (StrUtil.isEmpty(userBindDTO.getPassword())) {
      throw new BusinessException("原密码不能为空");
    }

    if (passwordEncoder
        .matches(userBindDTO.getPassword(), userDOOptional.get().getPassword())) {
      log.info("原密码错误，修改个人信息失败:{}", userBindDTO.getId());
      throw new BusinessException("修改密码失败，输入的旧密码错误");
    }

    if (StrUtil.isEmpty(userBindDTO.getNewPassword())) {
      throw new BusinessException("新密码不能为空");
    }

    String password = passwordEncoder.encode(userBindDTO.getNewPassword());

    UserDO userDO = userDOOptional.get();
    userDO.setPassword(password);
    userRepository.save(userDO);
  }

  @Override
  public Boolean checkPassword(UserBindDTO userBindDTO) {
    Optional<UserDO> userDOOptional = userRepository.findById(userBindDTO.getId());
    if (!userDOOptional.isPresent()) {
      throw new BusinessException("用户不存在");
    }

    if (StrUtil.isEmpty(userBindDTO.getPassword())) {
      throw new BusinessException("密码不能为空");
    }

    // 密码校验
    if (!passwordEncoder
        .matches(userBindDTO.getPassword(), userDOOptional.get().getPassword())) {
      log.info("用户:{} 校验密码错误", userBindDTO.getId());
      return false;
    }
    return true;
  }

  @Override
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public void bindUserTel(UserBindDTO userBindDTO) {

    // 用户电话不能为空
    if (StrUtil.isBlank(userBindDTO.getTel())) {
      throw new BusinessException("用户电话不能为空");
    }

    // 校验是否是手机号
    boolean mobile = PhoneUtil.isMobile(userBindDTO.getTel());
    if (!mobile) {
      throw new BusinessException("当前输入的手机号格式错误");
    }

    // 获取当前用户信息
    AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
    Optional<UserDO> optionalUserDO = userRepository.findById(authenticationUser.getId());
    if (!optionalUserDO.isPresent()) {
      throw new BusinessException("请先登录");
    }
    // 获取用户信息
    UserDO userDO = optionalUserDO.get();
    // 设置电话信息
    userDO.setTel(userBindDTO.getTel());
    userRepository.save(userDO);
  }

  @Override
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public void certification(UserBindDTO userBindDTO) {
    // 用户名和身份证不能为空
    if (StrUtil.isBlank(userBindDTO.getUserName()) || StrUtil.isBlank(userBindDTO.getIdentity())) {
      throw new BusinessException("用户名和身份证不能为空");
    }

    // 身份证号
    String identity = userBindDTO.getIdentity();
    // 校验书
    if (IdcardUtil.isValidCard(identity)) {
      throw new BusinessException("无效的身份证信息");
    }

    // 获取身份证年龄
    int age = IdcardUtil.getAgeByIdCard(identity);
    if (age < 16) {
      throw new BusinessException("禁止16岁以下的人使用");
    }

    // TODO 调用第三方API校验该实名认证用户信息是否正确

    // 获取当前用户信息
    AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
    Optional<UserDO> optionalUserDO = userRepository.findById(authenticationUser.getId());
    if (!optionalUserDO.isPresent()) {
      throw new BusinessException("请先登录");
    }
    // 获取用户信息
    UserDO userDO = optionalUserDO.get();
    // 设置姓名
    userDO.setUserName(userBindDTO.getUserName());
    // 设置身份证
    userDO.setIdentity(userBindDTO.getIdentity());
    userRepository.save(userDO);
  }
}