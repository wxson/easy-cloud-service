package com.easy.cloud.web.module.certification.biz.service.certification;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;
import com.easy.cloud.web.module.certification.biz.service.certification.entity.CertificationBody;

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
     * @param certificationBody 认证信息
     * @return
     */
    HttpResult<Object> certification(CertificationBody certificationBody);
}
