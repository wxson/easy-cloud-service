package com.easy.cloud.web.module.certification.biz.service.impl;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.FaceRecognitionClientEnum;
import com.easy.cloud.web.module.certification.biz.service.IFaceRecognitionService;
import com.easy.cloud.web.module.certification.biz.service.face.FaceRecognitionProperties;
import com.easy.cloud.web.module.certification.biz.service.face.IFaceRecognitionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author GR
 * @date 2024/2/19 20:43
 */
@Slf4j
@Service
public class FaceRecognitionServiceImpl implements IFaceRecognitionService, ApplicationContextAware {

    @Autowired
    private FaceRecognitionProperties faceRecognitionProperties;

    /**
     * 认证类型方式
     */
    private final Map<FaceRecognitionClientEnum, IFaceRecognitionClient> faceRecognitionClients = new EnumMap<>(FaceRecognitionClientEnum.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(IFaceRecognitionClient.class).values()
                .forEach(faceRecognitionClient -> faceRecognitionClients.put(faceRecognitionClient.client(), faceRecognitionClient));
    }

    @Override
    public HttpResult<Object> recognition() {
        return null;
    }
}
