package com.easy.cloud.web.service.upms.biz.social.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.module.sms.api.dto.SmsValidateCodeDTO;
import com.easy.cloud.web.module.sms.api.feign.SmsModuleApi;
import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 手机验证码
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
public class TelSocialServiceImpl implements ISocialService {

    @Autowired
    private SmsModuleApi smsModuleApi;

    @Override
    public SocialTypeEnum getType() {
        return SocialTypeEnum.TEL;
    }

    @Override
    public UserDO loadSocialUser(UserLoginDTO userLogin) {
        // 验证电话和验证码
        if (StrUtil.isBlank(userLogin.getTel()) || StrUtil.isBlank(userLogin.getCode())) {
            throw new BusinessException("当前电话号码或验证码不存在");
        }
        // 校验验证码是否有效
        Boolean status = smsModuleApi.validateSmsCode(SmsValidateCodeDTO.builder().tel(userLogin.getTel())
                .code(userLogin.getCode()).build()).getData();
        if (Objects.isNull(status) || !status) {
            throw new BusinessException("当前验证码已过期");
        }
        // 构建用户信息
        return UserDO.builder()
                .tel(userLogin.getTel())
                .userName(userLogin.getTel())
                .nickName(userLogin.getTel())
                .build();
    }
}
