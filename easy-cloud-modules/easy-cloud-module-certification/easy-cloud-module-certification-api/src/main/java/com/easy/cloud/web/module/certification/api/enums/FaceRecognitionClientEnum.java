package com.easy.cloud.web.module.certification.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 人脸识别类型
 *
 * @author GR
 * @date 2024/2/19 11:34
 */
@Getter
@AllArgsConstructor
public enum FaceRecognitionClientEnum implements IBaseEnum {
    /**
     * 审核状态：1-阿里云；2-腾讯云；
     */
    ALI(1, "阿里云"),
    TENCENT(2, "腾讯云"),
    ;
    private final int code;
    private final String desc;


    /**
     * 根据code获取对象
     *
     * @param code 编码
     * @return
     */
    public static Optional<FaceRecognitionClientEnum> getInstanceByCode(int code) {
        for (FaceRecognitionClientEnum faceRecognitionClientEnum : FaceRecognitionClientEnum.values()) {
            if (code == faceRecognitionClientEnum.code) {
                return Optional.of(faceRecognitionClientEnum);
            }
        }
        return Optional.empty();
    }
}
