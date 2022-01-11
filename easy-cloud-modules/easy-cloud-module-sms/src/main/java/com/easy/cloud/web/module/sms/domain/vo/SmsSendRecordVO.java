package com.easy.cloud.web.module.sms.domain.vo;

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
public class SmsSendRecordVO {
    /**
     * 文档ID
     */
    private Integer id;
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
    /**
     * 创建用户
     */
    private String creatorAt;
    /**
     * 创建时间
     */
    private String createAt;
}
