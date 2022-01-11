package com.easy.cloud.web.module.sms.domain.dto;

import com.easy.cloud.web.component.core.service.IConvertProxy;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 短信发送记录
 *
 * @author GR
 * @date 2021-12-9 18:53
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "build")
public class SmsDTO implements IConvertProxy {
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 短信验证码
     */
    private String code;
}
