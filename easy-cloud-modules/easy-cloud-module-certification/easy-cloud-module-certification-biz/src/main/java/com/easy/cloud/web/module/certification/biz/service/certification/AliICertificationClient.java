package com.easy.cloud.web.module.certification.biz.service.certification;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public CertificationClientEnum client() {
        return CertificationClientEnum.ALI;
    }

    @Override
    public HttpResult<Object> certification(String userName, String identityCard) {
        return HttpResult.ok();
    }
}
