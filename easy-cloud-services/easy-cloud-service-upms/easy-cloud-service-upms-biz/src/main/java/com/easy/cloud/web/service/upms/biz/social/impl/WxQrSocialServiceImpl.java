package com.easy.cloud.web.service.upms.biz.social.impl;

import com.easy.cloud.web.service.upms.api.dto.UserLoginDTO;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
public class WxQrSocialServiceImpl implements ISocialService {

    /**
     * APP_ID
     */
    private final String APP_ID = "XXXXX";
    /**
     * SECRET
     */
    private final String APP_SECRET = "XXXXXXXXXXXXXXXXXXXXXXX";

    @Override
    public SocialTypeEnum getType() {
        return SocialTypeEnum.WX_QR;
    }

    @Override
    public UserDO loadSocialUser(UserLoginDTO userLogin) {
        return null;
    }
}
