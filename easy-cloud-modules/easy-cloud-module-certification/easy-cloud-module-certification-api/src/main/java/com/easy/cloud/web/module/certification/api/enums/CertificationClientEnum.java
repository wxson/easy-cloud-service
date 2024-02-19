package com.easy.cloud.web.module.certification.api.enums;

import com.easy.cloud.web.component.core.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 认证类型
 *
 * @author GR
 * @date 2024/2/19 11:34
 */
@Getter
@AllArgsConstructor
public enum CertificationClientEnum implements IBaseEnum {
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
    public static Optional<CertificationClientEnum> getInstanceByCode(int code) {
        for (CertificationClientEnum certificationClientEnum : CertificationClientEnum.values()) {
            if (code == certificationClientEnum.code) {
                return Optional.of(certificationClientEnum);
            }
        }
        return Optional.empty();
    }
}
