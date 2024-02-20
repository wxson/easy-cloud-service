package com.easy.cloud.web.module.certification.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;
import com.easy.cloud.web.module.certification.biz.service.ICertificationService;
import com.easy.cloud.web.module.certification.biz.service.certification.CertificationProperties;
import com.easy.cloud.web.module.certification.biz.service.certification.ICertificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author GR
 * @date 2024/2/19 19:31
 */
@Slf4j
@Service
public class CertificationServiceImpl implements ICertificationService, ApplicationContextAware {

    @Autowired
    private CertificationProperties certificationProperties;

    /**
     * 认证类型方式
     */
    private final Map<CertificationClientEnum, ICertificationClient> certificationClients = new EnumMap<>(CertificationClientEnum.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(ICertificationClient.class).values()
                .forEach(certificationClient -> certificationClients.put(certificationClient.client(), certificationClient));
    }

    @Override
    public HttpResult<Object> certification(String userName, String identityCard) {
        if (Objects.isNull(certificationProperties)) {
            log.info("当前项目未配置第三方实名认证信息，默认使用身份证格式校验。");
            return HttpResult.ok();
        }

        // 通过渠道获取当前认证客户端
        if (StrUtil.isBlank(certificationProperties.getChannel())) {
            throw new BusinessException("当前未配置第三方认证信息");
        }

        // 获取配置渠道
        CertificationClientEnum client = CertificationClientEnum.valueOf(certificationProperties.getChannel());
        // 获取渠道实现对象
        ICertificationClient certificationClient = Optional.ofNullable(certificationClients.get(client))
                .orElseThrow(() -> new BusinessException(StrUtil.format("当前认证渠道不支持：%s", client)));
        return certificationClient.certification(userName, identityCard);
    }
}
