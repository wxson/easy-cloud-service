package com.easy.cloud.web.service.upms.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.log.annotation.SysLog;
import com.easy.cloud.web.component.log.annotation.SysLog.Action;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.component.security.domain.AuthenticationUser;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.api.dto.UserBindDTO;
import com.easy.cloud.web.service.upms.api.dto.UserDTO;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User API
 *
 * @author Fast Java
 * @date 2023-08-03 14:25:41
 */
@Slf4j
@RestController
@RequestMapping(value = "user")
public class UserController {

  @Autowired
  private IUserService userService;

  /**
   * 新增
   *
   * @param userDTO 新增数据
   * @return 新增数据
   */
  @PostMapping(value = "save")
  @PreAuthorize("@pms.hasPermission('system:user:add')")
  @SysLog(value = "新增用户", action = Action.ADD)
  @ApiOperation(value = "新增用户")
  public HttpResult<UserVO> save(@Validated @RequestBody UserDTO userDTO) {
    // 该接口绑定租户为当前登录用户
    userDTO.setTenantId(SecurityUtils.getAuthenticationUser().getTenant());
    return HttpResult.ok(userService.save(userDTO));
  }

  /**
   * 更新
   *
   * @param userDTO 新增数据
   * @return 更新数据
   */
  @PostMapping(value = "update")
  @PreAuthorize("@pms.hasPermission('system:user:update')")
  @SysLog(value = "更新用户", action = Action.UPDATE)
  @ApiOperation(value = "更新用户")
  public HttpResult<UserVO> update(@Validated @RequestBody UserDTO userDTO) {
    // 该接口绑定租户为当前登录用户
    userDTO.setTenantId(SecurityUtils.getAuthenticationUser().getTenant());
    return HttpResult.ok(userService.update(userDTO));
  }

  /**
   * 根据ID移除数据
   *
   * @param userId ID
   * @return 是否删除成功
   */
  @GetMapping(value = "remove/{userId}")
  @PreAuthorize("@pms.hasPermission('system:user:delete')")
  @SysLog(value = "删除用户", action = Action.DELETE)
  @ApiOperation(value = "删除用户")
  public HttpResult<Boolean> removeById(
      @PathVariable @NotNull(message = "当前ID不能为空") String userId) {
    return HttpResult.ok(userService.removeById(userId));
  }

  /**
   * 根据ID获取详情
   *
   * @param userId ID
   * @return 详情数据
   */
  @GetMapping(value = "detail/{userId}")
  @PreAuthorize("@pms.hasPermission('system:user:query')")
  @SysLog(value = "获取用户详情", action = Action.FIND)
  @ApiOperation(value = "获取用户详情")
  public HttpResult<UserVO> detailById(@PathVariable @NotNull(message = "当前ID不能为空") String userId) {
    return HttpResult.ok(userService.detailById(userId));
  }


  /**
   * 根据当前用户基本信息（登录用户的所有信息）
   *
   * @return 详情数据
   */
  @GetMapping(value = "base/detail")
  @PreAuthorize("@pms.hasPermission('system:user:query')")
  @SysLog(value = "获取用户基本详情", action = Action.FIND)
  @ApiOperation(value = "获取用户基本详情")
  public HttpResult<UserVO> baseDetail() {
    return HttpResult.ok(userService.detailById(SecurityUtils.getAuthenticationUser().getId()));
  }

  /**
   * TODO 所有数据列表，查询参数自定义
   *
   * @return 查询列表
   */
  @GetMapping(value = "list")
  @PreAuthorize("@pms.hasPermission('system:user:query')")
  @SysLog(value = "用户列表", action = Action.FIND)
  @ApiOperation(value = "用户列表")
  public HttpResult<List<UserVO>> list() {
    return HttpResult.ok(userService.list());
  }

  /**
   * TODO 根据条件查询分页数据，查询参数自定义
   *
   * @param page 当前页
   * @param size 每页大小
   * @return 查询分页数据
   */
  @GetMapping(value = "page")
  @PreAuthorize("@pms.hasPermission('system:user:query')")
  @SysLog(value = "用户分页", action = Action.FIND)
  @ApiOperation(value = "用户分页")
  public HttpResult<Page<UserVO>> page(@RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {
    return HttpResult.ok(userService.page(page, size));
  }


  /**
   * 根据登录名获取用户详情
   *
   * @param userName 登录名
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.v1.domain.vo.UserVO>
   */
  @Inner
  @GetMapping("detail/{userName}")
  @SysLog(value = "根据用户名获取用户详情", action = Action.FIND)
  @ApiOperation(value = "根据用户名获取用户详情")
  public HttpResult<UserVO> loadUserByUsername(@PathVariable @ApiParam("用户账号") String userName) {
    return HttpResult.ok(userService.loadUserByUsername(userName));
  }

  /**
   * 根据传入对象获取用户详情
   *
   * @param type         类型：微信、QQ
   * @param userLoginDTO 第三方类型
   * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.v1.domain.vo.UserVO>
   */
  @Inner
  @PostMapping("detail/{type}")
  @SysLog(value = "根据传入对象获取用户详情", action = Action.FIND)
  @ApiOperation(value = "根据传入对象获取用户详情")
  public HttpResult<UserVO> loadSocialUserByObject(@PathVariable String type,
      @RequestBody UserLoginDTO userLoginDTO) {
    return HttpResult.ok(userService.loadSocialUser(type, userLoginDTO));
  }

  /**
   * 绑定角色
   *
   * @param userBindDTO 用户信息
   * @return success/false
   */
  @PostMapping("/bind/role")
  @PreAuthorize("@pms.hasPermission('system:user:update')")
  @SysLog(value = "绑定用户角色", action = Action.UPDATE)
  @ApiOperation(value = "绑定用户角色")
  public HttpResult<UserVO> bindUserRole(@RequestBody UserBindDTO userBindDTO) {
    AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
    userBindDTO.setId(authenticationUser.getId());
    return HttpResult.ok(userService.bindUserRole(userBindDTO));
  }


  /**
   * 注册用户
   *
   * @param userDto 用户信息
   * @return success/false
   */
  @Inner
  @PostMapping("/register")
  @SysLog(value = "注册用户", action = Action.ADD)
  @ApiOperation(value = "注册用户")
  public HttpResult<UserVO> registerUser(@RequestBody UserDTO userDto) {
    UserVO userVO = userService.registerUser(userDto);
    return HttpResult.ok(userVO);
  }


  /**
   * 锁定指定用户
   *
   * @param userId 用户ID
   * @return HttpResult
   */
  @Inner
  @PostMapping("/lock/{userId}")
  @SysLog(value = "锁定指定用户", action = Action.UPDATE)
  @ApiOperation(value = "锁定指定用户")
  public HttpResult<UserVO> lockUser(@PathVariable String userId) {
    return HttpResult.ok(userService.lockUser(userId));
  }

  /**
   * 修改密码
   *
   * @param userBindDTO 修改密码参数
   * @return HttpResult
   */
  @PostMapping("/password")
  @SysLog(value = "修改用户密码", action = Action.UPDATE)
  @ApiOperation(value = "修改用户密码")
  public HttpResult<Boolean> changePassword(@RequestBody UserBindDTO userBindDTO) {
    AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
    userBindDTO.setId(authenticationUser.getId());
    userService.changePassword(userBindDTO);
    return HttpResult.ok(true);
  }

  /**
   * 密码校验
   *
   * @param userBindDTO 密码校验数据
   * @return HttpResult
   */
  @PostMapping("/check")
  @SysLog(value = "校验用户密码", action = Action.FIND)
  @ApiOperation(value = "校验用户密码")
  public HttpResult<String> checkPassword(@RequestBody UserBindDTO userBindDTO) {
    AuthenticationUser authenticationUser = SecurityUtils.getAuthenticationUser();
    userBindDTO.setId(authenticationUser.getId());
    Boolean checkPassword = userService.checkPassword(userBindDTO);
    if (checkPassword) {
      return HttpResult.ok("ok");
    }
    return HttpResult.fail("密码错误");
  }

  /**
   * 实名认证
   *
   * @param userBindDTO 实名认证信息
   * @return void
   */
  @PostMapping("certification")
  @SysLog(value = "实名认证", action = Action.UPDATE)
  @ApiOperation(value = "实名认证")
  public HttpResult<Boolean> certification(@RequestBody UserBindDTO userBindDTO) {
    userService.certification(userBindDTO);
    return HttpResult.ok(true);
  }

  /**
   * 实名认证
   *
   * @param userBindDTO 实名认证信息
   * @return void
   */
  @PostMapping("bind/tel")
  @SysLog(value = "绑定用户电话", action = Action.UPDATE)
  @ApiOperation(value = "绑定用户电话")
  public HttpResult<Boolean> bindUserTel(@RequestBody UserBindDTO userBindDTO) {
    userService.bindUserTel(userBindDTO);
    return HttpResult.ok(true);
  }
}