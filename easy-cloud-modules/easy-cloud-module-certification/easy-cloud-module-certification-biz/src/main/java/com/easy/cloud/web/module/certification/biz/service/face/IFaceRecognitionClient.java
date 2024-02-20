package com.easy.cloud.web.module.certification.biz.service.face;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.FaceRecognitionClientEnum;

/**
 * @author GR
 * @date 2024/2/19 20:46
 */
public interface IFaceRecognitionClient {

    /**
     * 识别客户端
     *
     * @return
     */
    FaceRecognitionClientEnum client();

    /**
     * 识别
     *
     * @return
     */
    HttpResult<Object> recognition();
}
