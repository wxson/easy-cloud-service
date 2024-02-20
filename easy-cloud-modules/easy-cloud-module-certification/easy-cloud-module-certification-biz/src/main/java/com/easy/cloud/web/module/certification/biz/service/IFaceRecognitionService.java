package com.easy.cloud.web.module.certification.biz.service;

import com.easy.cloud.web.component.core.response.HttpResult;

/**
 * 第三方人脸识别逻辑
 *
 * @author GR
 * @date 2024/2/19 18:34
 */
public interface IFaceRecognitionService {

    /**
     * 人脸识别
     *
     * @return
     */
    HttpResult<Object> recognition();
}
