package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.enums.StatusEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.BeanUtils;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.api.dto.UserBindDTO;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.enums.GenderEnum;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.api.vo.RoleVO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.constant.UpmsCacheConstants;
import com.easy.cloud.web.service.upms.biz.constant.UpmsConstants;
import com.easy.cloud.web.service.upms.biz.converter.UserConverter;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.domain.UserRoleDO;
import com.easy.cloud.web.service.upms.biz.repository.RoleMenuRepository;
import com.easy.cloud.web.service.upms.biz.repository.UserRepository;
import com.easy.cloud.web.service.upms.biz.repository.UserRoleRepository;
import com.easy.cloud.web.service.upms.biz.service.IMenuService;
import com.easy.cloud.web.service.upms.biz.service.IRoleService;
import com.easy.cloud.web.service.upms.biz.service.IUserService;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IMenuService menuService;

  @Autowired
  private IRoleService roleService;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Autowired
  private RoleMenuRepository roleMenuRepository;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    applicationContext.getBeansOfType(ISocialService.class).values()
        .forEach(socialService -> socialServices.put(socialService.getType(), socialService));
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void init() {
    // 未初始化过数据
    if (userRepository.count() <= 0) {
      // 获取超级管理员角色
      RoleVO superAdminRole = roleService.findFirstByCode(GlobalCommonConstants.SUPER_ADMIN_ROLE);
      // 创建超管
      UserDO admin = UserDO.builder()
          .nickName(GlobalCommonConstants.SUPER_ADMIN_ROLE)
          .userName(GlobalCommonConstants.SUPER_ADMIN_ROLE)
          .password(passwordEncoder.encode(UpmsConstants.SUPER_ADMIN_PASSWORD))
          .tenantId(GlobalCommonConstants.DEFAULT_TENANT_ID_VALUE + "")
          .gender(GenderEnum.MAN)
          .build();
      userRepository.save(admin);
      // 绑定超管角色
      UserRoleDO userRoleDO = UserRoleDO.builder()
          .userId(admin.getId())
          .roleId(superAdminRole.getId())
          .build();
      userRoleRepository.save(userRoleDO);
      log.info("init platform user content success!");
    }
  }

  @Override
  @Transactional
  public UserVO save(UserDTO userDTO) {
    // 转换成DO对象
    UserDO user = UserConverter.convertTo(userDTO);
    // TODO 校验逻辑
    // 若昵称为空，则根据账号或电话生成默认昵称
    if (StrUtil.isBlank(userDTO.getNickName())) {
      // 昵称=账号
      user.setNickName(userDTO.getUserName());
    }

    // 密码编译
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    // 存储
    userRepository.save(user);
    // 更新用户角色信息
    this.updateUserRole(user, userDTO);
    // 转换对象
    return UserConverter.convertTo(user);
  }

  @Override
  @Transactional
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public UserVO update(UserDTO userDTO) {
    // 转换成DO对象
    if (Objects.isNull(userDTO.getId())) {
      throw new RuntimeException("当前更新对象ID为空");
    }
    // TODO 业务逻辑校验
    UserDO user = userRepository.findById(userDTO.getId())
        .orElseThrow(() -> new BusinessException("当前菜单信息不存在"));
    // 保留密码，修改密码采用单独的接口修改
    String oldPassword = user.getPassword();
    // 将修改的数据赋值给数据库数据
    BeanUtils.copyProperties(userDTO, user, true);
    user.setPassword(oldPassword);
    // 更新
    userRepository.save(user);
    // 更新用户角色信息
    this.updateUserRole(user, userDTO);

    // 转换对象
    return UserConverter.convertTo(user);
  }

  /**
   * 更新用户角色信息
   *
   * @param userDO  用户信息
   * @param userDTO 绑定信息
   */
  private void updateUserRole(UserDO userDO, UserDTO userDTO) {
    // 更新用户角色信息:先删除，后添加
    userRoleRepository.deleteByUserId(userDO.getId());
    // 如果绑定有角色ID，则存储
    if (CollUtil.isNotEmpty(userDTO.getRoleIds())) {
      // 构建关系表
      List<UserRoleDO> userRoles = userDTO.getRoleIds().stream()
          .map(roleId -> UserRoleDO.builder()
              .userId(userDO.getId())
              .roleId(roleId)
              .build())
          .collect(Collectors.toList());
      userRoleRepository.saveAll(userRoles);
    }
  }

  @Override
  @Transactional
  @CacheEvict(value = UpmsCacheConstants.USER_DETAILS, allEntries = true)
  public Boolean removeById(String userId) {
    // TODO 业务逻辑校验

    // 删除
    userRepository.logicDelete(userId);
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
    userVO.setPassword("N/A");
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
    List<String> roleIds = userRoleRepository.findByUserId(userDO.getId()).stream()
        .map(UserRoleDO::getRoleId)
        .distinct()
        .collect(Collectors.toList());
    // 根据角色ID获取角色编码
    Set<String> roleCodes = roleService.findAllByIds(roleIds).stream()
        .map(RoleVO::getCode)
        .collect(Collectors.toSet());
    // 设置角色列表 （ID）
    userVO.setRoles(roleCodes);
    // 获取所有有关的菜单权限标识
    Set<String> permissions = menuService
        .findPermissionsByRoleCodes(CollUtil.newArrayList(roleCodes));
    // 设置权限列表（menu.permission）
    userVO.setPermissions(permissions);
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
    Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
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
    List<String> roleIds = userBindDTO.getRoleIds();
    if (CollUtil.isEmpty(roleIds)) {
      return userDOOptional.get().convertTo(UserVO.class);
    }

    // 移除旧数据
    userRoleRepository.deleteByUserId(userBindDTO.getId());
    // 添加新数据
    List<UserRoleDO> userRoles = roleIds.stream().map(
        roleId -> UserRoleDO.builder().userId(userBindDTO.getId()).roleId(roleId).build())
        .collect(Collectors.toList());
    // 批量存储
    userRoleRepository.saveAll(userRoles);
    UserDO userDO = userDOOptional.get();
    UserVO userVO = userDO.convertTo(UserVO.class);
    // 根据角色ID获取角色编码
    Set<String> roleCodes = roleService.findAllByIds(roleIds).stream()
        .map(RoleVO::getCode)
        .collect(Collectors.toSet());
    // 设置角色列表 （ID）
    userVO.setRoles(roleCodes);
    return userVO;
  }

  @Override
  public UserVO loadUserByUsername(String userName) {
    // 若用户名为空，则返回
    if (StrUtil.isBlank(userName)) {
      throw new BusinessException("登录名不能为空");
    }
    // 用户名
    UserDO userDO = userRepository.findByUserName(userName);
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
        .findByUnionIdOrAppleId(userDO.getUnionId(), userDO.getAppleId());
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
    if (StringUtils.isBlank(userDto.getUserName())) {
      throw new BusinessException("账号信息为空");
    }
    UserDO existUser = userRepository.findByUserName(userDto.getUserName());
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