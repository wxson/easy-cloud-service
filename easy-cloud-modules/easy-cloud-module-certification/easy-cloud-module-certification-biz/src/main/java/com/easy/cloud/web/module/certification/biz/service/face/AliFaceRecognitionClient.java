package com.easy.cloud.web.module.certification.biz.service.face;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.FaceRecognitionClientEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云人脸识别
 *
 * @author GR
 * @date 2024/2/19 20:47
 */
@Slf4j
@Service
public class AliFaceRecognitionClient implements IFaceRecognitionClient {

    @Autowired
    private FaceRecognitionProperties faceRecognitionProperties;

    @Override
    public FaceRecognitionClientEnum client() {
        return FaceRecognitionClientEnum.ALI;
    }

    @Override
    public HttpResult<Object> recognition() {
        return null;
    }
}
