package com.easy.cloud.web.service.upms.biz.social.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.upms.biz.domain.db.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * QQ
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class QqSocialServiceImpl implements ISocialService {

    @Override
    public UserDO loadSocialUser(String code) {
        if (StrUtil.isBlank(code)) {
            throw new BusinessException("当前登录信息不能为空");
        }

        // 解析JSON
        UserDO userDO = JSONUtil.toBean(code, UserDO.class);
        if (StrUtil.isBlank(userDO.getUnionId())) {
            throw new BusinessException("获取用户unionId信息失败");
        }
        
        return userDO;
    }
}
