package com.easy.cloud.web.module.certification.biz.service.certification;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;

/**
 * @author GR
 * @date 2024/2/19 19:37
 */
public interface ICertificationClient {

    /**
     * 认证客户端
     *
     * @return
     */
    CertificationClientEnum client();

    /**
     * 认证
     *
     * @param userName     用户名
     * @param identityCard 身份证
     * @return
     */
    HttpResult<Object> certification(String userName, String identityCard);
}
