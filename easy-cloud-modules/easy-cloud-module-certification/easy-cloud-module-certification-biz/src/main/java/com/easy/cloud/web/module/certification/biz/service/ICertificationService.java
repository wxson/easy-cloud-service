package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.component.core.response.HttpResult;

/**
 * 第三方实名认证逻辑
 *
 * @author GR
 * @date 2024/2/19 18:34
 */
public interface ICertificationService {

    /**
     * 认证
     *
     * @param userName     用户名
     * @param identityCard 身份证
     * @return
     */
    HttpResult<Object> certification(String userName, String identityCard);
}
