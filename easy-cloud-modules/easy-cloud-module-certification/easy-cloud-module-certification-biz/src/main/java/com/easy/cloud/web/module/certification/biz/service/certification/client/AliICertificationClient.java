package com.easy.cloud.web.module.certification.biz.service.certification.client;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;
import com.easy.cloud.web.module.certification.biz.service.certification.CertificationProperties;
import com.easy.cloud.web.module.certification.biz.service.certification.ICertificationClient;
import com.easy.cloud.web.module.certification.biz.service.certification.entity.CertificationBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云认证
 *
 * @author GR
 * @date 2024/2/19 19:37
 */
@Slf4j
@Service
public class AliICertificationClient implements ICertificationClient {

    @Autowired
    private CertificationProperties certificationProperties;

    @Override
    public CertificationClientEnum client() {
        return CertificationClientEnum.ALI;
    }

    @Override
    public HttpResult<Object> certification(CertificationBody certificationBody) {
        return HttpResult.ok();
    }
}
