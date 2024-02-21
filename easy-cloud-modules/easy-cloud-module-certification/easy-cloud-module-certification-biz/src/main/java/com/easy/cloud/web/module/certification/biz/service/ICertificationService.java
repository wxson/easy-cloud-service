package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.biz.service.certification.entity.CertificationBody;

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
     * @param certificationBody 用户认证信息
     * @return
     */
    HttpResult<Object> certification(CertificationBody certificationBody);
}
