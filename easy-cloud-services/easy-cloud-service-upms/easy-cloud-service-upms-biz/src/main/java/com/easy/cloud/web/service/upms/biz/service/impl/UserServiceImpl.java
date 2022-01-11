package com.easy.cloud.web.service.upms.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.cloud.web.component.core.constants.GlobalConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.SnowflakeUtils;
import com.easy.cloud.web.service.member.api.domain.dto.MemberDTO;
import com.easy.cloud.web.service.member.api.feign.MemberFeignClientService;
import com.easy.cloud.web.service.upms.biz.domain.db.UserDO;
import com.easy.cloud.web.service.upms.biz.domain.dto.UserDTO;
import com.easy.cloud.web.service.upms.biz.domain.vo.UserVO;
import com.easy.cloud.web.service.upms.biz.enums.RoleEnum;
import com.easy.cloud.web.service.upms.biz.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.mapper.UserMapper;
import com.easy.cloud.web.service.upms.biz.service.IUserService;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User 业务逻辑层
 *
 * @author Fast Java
 * @date 2021-03-16
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {

    private final MemberFeignClientService memberFeignClientService;

    @Override
    public UserDO verifyBeforeSave(UserDO userDO) {
        // 校验规则：用户账号、租户ID
        UserDO existUserDO = this.getOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUnionId, userDO.getUnionId()));
        // 校验用户账号是否已存在
        if (null != existUserDO) {
            throw new BusinessException("当前用户账号已存在");
        }

        this.allotAccount(userDO);
        // 分配用户ID
        userDO.setId(SnowflakeUtils.next());

        return userDO;
    }

    /**
     * 分配唯一账号
     *
     * @param userDO 新增用户信息
     */
    private synchronized void allotAccount(UserDO userDO) {
        // 获取当前人数
        int count = this.count();
        String account = StrUtil.toString(100000000 + count);
        userDO.setAccount(account);
    }

    @Override
    public void verifyAfterSave(UserDO userDO) {
        // 保存玩家信息后初始化玩家信息
        MemberDTO memberDTO = MemberDTO.build();
        memberDTO.setUserId(userDO.getId())
                .setAmount(GlobalConstants.L_ZERO)
                .setDiamond(GlobalConstants.L_ZERO)
                .setCoupon(GlobalConstants.L_ZERO);
        memberFeignClientService.initMemberInfo(memberDTO);
    }

    @Override
    public void verifyAfterDelete(Serializable userId) {
        // 删除用户之后，移除该用户的所有相关角色
        this.removeRelationUserRoleByUserId(userId);
    }

    @Override
    public void verifyIsContainSpecialRoleAndUpdate(UserDTO userDTO) {
        // 当前分配角色列表为空 或  当前用户ID为空
        if (CollUtil.isEmpty(userDTO.getRoleIdList()) || StrUtil.isBlank(userDTO.getId())) {
            return;
        }

        // 获取所有要分配的角色ID列表
        List<String> roleIdList = userDTO.getRoleIdList();
        // 若当前用户的角色存在以下其一，则当前用户的tenantId为其本身
        if (roleIdList.contains(RoleEnum.ROLE_SUPER_ADMIN.getId())
                || roleIdList.contains(RoleEnum.ROLE_ADMIN.getId())
                || roleIdList.contains(RoleEnum.ROLE_TENANT.getId())) {
            // 执行更新
//            this.update(Wrappers.<UserDO>lambdaUpdate().eq(UserDO::getId, userDTO.getId())
//                    .set(UserDO::getTenantId, userDTO.getId()));
        }
    }

    @Override
    public UserVO loadSocialUser(String type, String code) {
        Optional<ISocialService> socialServiceOptional = SocialTypeEnum.getSocialService(type);
        if (socialServiceOptional.isPresent()) {
            // 获取对应的用户信息
            UserDO userDO = socialServiceOptional.get().loadSocialUser(code);
            // 判断当前玩家是否为已注册用户
            UserDO existUser = this.getOne(Wrappers.<UserDO>lambdaQuery()
                    .eq(StrUtil.isNotBlank(userDO.getUnionId()), UserDO::getUnionId, userDO.getUnionId())
                    .eq(StrUtil.isNotBlank(userDO.getAppleId()), UserDO::getAppleId, userDO.getAppleId()));
            // 若未注册，则自动注册
            if (Objects.isNull(existUser)) {
                this.save(userDO);
                return userDO.convert();
            }
            return existUser.convert();
        }
        return null;
    }

    @Override
    public UserVO loadSocialUserByObject(String type, UserDTO userDTO) {
        log.info("当前登录方式：{}，登录信息：{}", type, userDTO);
        Optional<ISocialService> socialServiceOptional = SocialTypeEnum.getSocialService(type);
        if (socialServiceOptional.isPresent()) {
            // 获取对应的用户信息
            UserDO userDO = socialServiceOptional.get().loadSocialUser(JSONUtil.toJsonStr(userDTO));
            // 判断当前玩家是否为已注册用户
            UserDO existUser = this.getOne(Wrappers.<UserDO>lambdaQuery()
                    .eq(StrUtil.isNotBlank(userDO.getUnionId()), UserDO::getUnionId, userDO.getUnionId())
                    .eq(StrUtil.isNotBlank(userDO.getAppleId()), UserDO::getAppleId, userDO.getAppleId()));
            // 若未注册，则自动注册
            if (Objects.isNull(existUser)) {
                this.save(userDO);
                log.info("当前用户为新用户：{}", userDO);
                return userDO.convert();
            }
            return existUser.convert();
        }
        return null;
    }
}