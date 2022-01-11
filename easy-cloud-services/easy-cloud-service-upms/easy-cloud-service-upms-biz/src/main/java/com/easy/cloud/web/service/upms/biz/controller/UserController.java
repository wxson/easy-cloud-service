package com.easy.cloud.web.service.upms.biz.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.component.mysql.controller.BaseController;
import com.easy.cloud.web.component.mysql.service.IRepositoryService;
import com.easy.cloud.web.component.security.annotation.Inner;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.service.upms.biz.domain.db.UserDO;
import com.easy.cloud.web.service.upms.biz.domain.dto.UserDTO;
import com.easy.cloud.web.service.upms.biz.domain.query.UserQuery;
import com.easy.cloud.web.service.upms.biz.domain.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Fast Java
 * @date 2021-03-16
 */
@Slf4j
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api(value = "用户管理", tags = "用户管理")
public class UserController extends BaseController<UserQuery, UserDTO, UserDO> {

    private final IUserService userService;

    @Override
    public IRepositoryService<UserDO> getService() {
        return userService;
    }


    /**
     * 重写角色存储逻辑：存储角色时，可顺带存储当前角色分配的权限
     *
     * @param userDTO 用户信息
     * @return reactor.core.publisher.Mono<com.easy.cloud.web.common.response.HttpResponse>
     */
    @Override
    public HttpResult<Boolean> save(@Validated @RequestBody UserDTO userDTO) {
        UserDO userDO = userDTO.convert();
        this.getService().save(userDO);
        // 执行租户校验更新
        userService.verifyIsContainSpecialRoleAndUpdate(userDTO.setId(userDO.getId()));
        // 保存用户角色
        if (CollUtil.isNotEmpty(userDTO.getRoleIdList())) {
            userService.saveRelationUserRoleList(userDO.getId(), userDTO.getRoleIdList());
        }

        return HttpResult.ok();
    }

    /**
     * 分配角色：暂时采取方案一
     * 方案一：全部删除之前的用户角色信息，再统一添加新的用户角色
     * 方案二：获取原先的角色与新的角色进行对比，缺少的删除，多出来的新增，相同的保留（业务逻辑相对复杂些）
     *
     * @param dto 用户全新信息
     * @return reactor.core.publisher.Mono<com.easy.cloud.web.common.response.HttpResponse>
     */
    @PostMapping("allot/role")
    @ApiOperation(value = "分配角色")
    public HttpResult<Boolean> allotUserRoleList(@Validated @RequestBody UserDTO dto) {
        if (StrUtil.isBlank(dto.getId())) {
            throw new BusinessException("当前用户ID不能为空");
        }

        UserDO userDO = userService.getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getId, dto.getId()));
        if (null == userDO) {
            throw new BusinessException("当前用户信息不存在");
        }

        // 执行租户校验更新
        userService.verifyIsContainSpecialRoleAndUpdate(dto);

        // 当前分配角色不为空
        if (CollUtil.isNotEmpty(dto.getRoleIdList())) {
            // 移除当前用户的所有角色
            userService.removeRelationUserRoleByUserId(dto.getId());
            // 为当前用户指派新的角色列表
            userService.saveRelationUserRoleList(dto.getId(), dto.getRoleIdList());
        }


        return HttpResult.ok();
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userIdList 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.domain.vo.UserVO>
     */
    @PostMapping("batch/list")
    @ApiOperation(value = "根据用户ID获取用户信息")
    public HttpResult<List<UserVO>> batchList(@RequestBody List<String> userIdList) {
        if (CollUtil.isEmpty(userIdList)) {
            return HttpResult.ok(CollUtil.newArrayList());
        }

        return HttpResult.ok(userService.listByIds(userIdList).stream().map(UserDO::convert).collect(Collectors.toList()));
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.domain.vo.UserVO>
     */
    @Inner
    @GetMapping("info/{userId}")
    @ApiOperation(value = "根据用户ID获取用户信息")
    public HttpResult<UserVO> getUserInfo(@PathVariable String userId) {
        if (StrUtil.isBlank(userId)) {
            userId = SecurityUtils.getAuthenticationUser().getId();
        }

        // id不能为空
        if (StrUtil.isBlank(userId)) {
            throw new BusinessException("当前用户异常");
        }

        Optional<UserDO> optionalUserDO = Optional.ofNullable(userService.getById(userId));
        if (!optionalUserDO.isPresent()) {
            throw new BusinessException("获取当前用户信息异常");
        }

        // 当前用户信息
        UserVO userVO = optionalUserDO.get().convert();
        userVO.setPassword("N/A");
        return HttpResult.ok(userVO);
    }


    /**
     * 根据账号获取用户信息
     *
     * @param account 用户账号
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.domain.vo.UserVO>
     */
    @Inner
    @GetMapping("account/{account}")
    @ApiOperation(value = "根据用户ID获取用户信息")
    public HttpResult<UserVO> getUserByAccount(@PathVariable String account) {
        if (StrUtil.isBlank(account)) {
            throw new BusinessException("用户账号不能为空");
        }

        // 根据账号查询
        Optional<UserDO> optionalUserDO = Optional.ofNullable(userService.getOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getAccount, account)));
        if (!optionalUserDO.isPresent()) {
            throw new BusinessException("获取当前用户信息异常");
        }

        // 当前用户信息
        UserVO userVO = optionalUserDO.get().convert();
        userVO.setPassword("N/A");
        return HttpResult.ok(userVO);
    }

    /**
     * 根据登录名获取用户详情
     *
     * @param userName 登录名
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.domain.vo.UserVO>
     */
    @Inner
    @GetMapping("detail/{userName}")
    @ApiOperation(value = "根据用户名获取用户详情")
    public HttpResult<UserVO> loadUserByUsername(@PathVariable @ApiParam("用户账号") String userName) {
        // 若unionId为为空，则返回
        if (StrUtil.isBlank(userName)) {
            throw new BusinessException("登录名不能为空");
        }
        // 用户名
        UserDO userDO = userService.getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getAccount, userName));
        if (Objects.isNull(userDO)) {
            throw new BusinessException("当前用户不存在");
        }

        return HttpResult.ok(userDO.convert());
    }

    /**
     * 根据传入对象获取用户详情
     *
     * @param type    类型：微信、QQ
     * @param userDTO 第三方类型
     * @return com.easy.cloud.web.component.core.response.HttpResult<com.easy.cloud.web.service.upms.biz.domain.vo.UserVO>
     */
    @Inner
    @PostMapping("detail/{type}")
    @ApiOperation(value = "根据传入对象获取用户详情")
    public HttpResult<UserVO> loadSocialUserByObject(@PathVariable String type, @RequestBody UserDTO userDTO) {
        // Oauth2.0 code授权模式
        if (StrUtil.isNotBlank(userDTO.getCode())) {
            return HttpResult.ok(userService.loadSocialUser(type, userDTO.getCode()));
        }
        // 第三方Share SDK 授权模式
        return HttpResult.ok(userService.loadSocialUserByObject(type, userDTO));
    }

    /**
     * 实名认证
     *
     * @param userDTO 实名认证信息
     * @return void
     */
    @PostMapping("certification")
    @ApiOperation(value = "实名认证")
    public HttpResult<Boolean> certification(@RequestBody UserDTO userDTO) {
        // 用户名和身份证不能为空
        if (StrUtil.isBlank(userDTO.getUserName()) || StrUtil.isBlank(userDTO.getIdentity())) {
            throw new BusinessException("用户名和身份证不能为空");
        }

        // 身份证号
        String identity = userDTO.getIdentity();
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

        return HttpResult.ok(false);
    }

    /**
     * 实名认证
     *
     * @param userDTO 实名认证信息
     * @return void
     */
    @PostMapping("bind/tel")
    @ApiOperation(value = "实名认证")
    public HttpResult<Boolean> bindUserTel(@RequestBody UserDTO userDTO) {
        // 判断当前用户ID
        if (StrUtil.isBlank(userDTO.getId())) {
            userDTO.setId(SecurityUtils.getAuthenticationUser().getId());
            if (StrUtil.isBlank(userDTO.getId())) {
                throw new BusinessException("获取当前用户信息失败");
            }
        }

        // 用户名和身份证不能为空
        if (StrUtil.isBlank(userDTO.getTel())) {
            throw new BusinessException("用户电话不能为空");
        }

        // 校验是否是手机号
        boolean mobile = PhoneUtil.isMobile(userDTO.getTel());
        if (!mobile) {
            throw new BusinessException("当前输入的手机号格式错误");
        }

        // 更新手机号
        boolean update = userService.update(Wrappers.<UserDO>lambdaUpdate().eq(UserDO::getId, userDTO.getId())
                .set(UserDO::getTel, userDTO.getTel()));


        return HttpResult.ok(update);
    }
}