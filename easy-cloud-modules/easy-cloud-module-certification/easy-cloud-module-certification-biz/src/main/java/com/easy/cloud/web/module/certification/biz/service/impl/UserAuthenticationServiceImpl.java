package com.easy.cloud.web.module.certification.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.security.util.SecurityUtils;
import com.easy.cloud.web.module.certification.api.dto.UserAuthenticationDTO;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationStatusEnum;
import com.easy.cloud.web.module.certification.api.enums.AuthenticationTypeEnum;
import com.easy.cloud.web.module.certification.api.vo.UserAuthenticationVO;
import com.easy.cloud.web.module.certification.biz.converter.UserAuthenticationConverter;
import com.easy.cloud.web.module.certification.biz.domain.UserAuthenticationDO;
import com.easy.cloud.web.module.certification.biz.repository.UserAuthenticationRepository;
import com.easy.cloud.web.module.certification.biz.service.IUserAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author GR
 * @date 2024/2/21 15:41
 */
@Slf4j
@Service
public class UserAuthenticationServiceImpl implements IUserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public UserAuthenticationVO addOrUpdate(UserAuthenticationDTO userAuthenticationDTO) {
        // 转换对象
        UserAuthenticationDO newUserAuthenticationDO = UserAuthenticationConverter.convertTo(userAuthenticationDTO);
        // 获取登录用户ID
        String userId = SecurityUtils.getAuthenticationUser().getId();
        // 设置认证账号
        newUserAuthenticationDO.setUserId(userId);
        // 查询当前是否存在认证信息
        Optional<UserAuthenticationDO> userAuthenticationOptional = userAuthenticationRepository.findById(userId);
        // 不存在认证信息，则新增认证信息
        if (!userAuthenticationOptional.isPresent()) {
            // 已认证
            newUserAuthenticationDO.setAuthenticated(true);
            newUserAuthenticationDO.setAuthenticationStatus(AuthenticationStatusEnum.SUCCESS);
            // 存储
            userAuthenticationRepository.save(newUserAuthenticationDO);
            // 转换对象
            return UserAuthenticationConverter.convertTo(newUserAuthenticationDO);
        }

        // 存在，获取认证信息
        UserAuthenticationDO userAuthenticationDO = userAuthenticationOptional.get();
        // 当前认证信息为个企业认证，则禁止更新认证信息
        if (AuthenticationTypeEnum.Company == userAuthenticationDO.getAuthenticationType()) {
            // 转换对象
            return UserAuthenticationConverter.convertTo(userAuthenticationDO);
        }

        // 若已存在个人认证，且当前认证为个人认证，则跳过
        if (AuthenticationTypeEnum.Personal == userAuthenticationDO.getAuthenticationType()
                && AuthenticationTypeEnum.Personal == newUserAuthenticationDO.getAuthenticationType()) {
            // 转换对象
            return UserAuthenticationConverter.convertTo(userAuthenticationDO);
        }

        // 若已存在个人认证，且当前认证为企业认证，则允许修改，优先级：企业认证 > 个人认证
        userAuthenticationDO.setAuthenticated(true);
        userAuthenticationDO.setAuthenticationType(AuthenticationTypeEnum.Company);
        userAuthenticationDO.setAuthenticationStatus(AuthenticationStatusEnum.SUCCESS);
        // 存储
        userAuthenticationRepository.save(newUserAuthenticationDO);
        // 转换对象
        return UserAuthenticationConverter.convertTo(userAuthenticationDO);
    }

    @Override
    public UserAuthenticationVO detailById(String userId) {
        // TODO 业务逻辑校验

        // 获取登录用户ID
        if (StrUtil.isBlank(userId)) {
            userId = SecurityUtils.getAuthenticationUser().getId();
        }

        // 查询
        UserAuthenticationDO userAuthenticationDO = userAuthenticationRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("当前数据不存在"));
        // 转换对象
        return UserAuthenticationConverter.convertTo(userAuthenticationDO);
    }
}
